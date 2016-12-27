package ch.logixisland.anuto.game.business.control;

import ch.logixisland.anuto.game.business.score.ScoreBoard;
import ch.logixisland.anuto.game.engine.GameEngine;
import ch.logixisland.anuto.game.entity.Entity;
import ch.logixisland.anuto.game.entity.Types;
import ch.logixisland.anuto.game.entity.plateau.Plateau;
import ch.logixisland.anuto.game.entity.tower.Tower;
import ch.logixisland.anuto.util.math.vector.Vector2;

public class TowerInserter {

    private final GameEngine mGameEngine;
    private final TowerSelector mTowerSelector;
    private final ScoreBoard mScoreBoard;

    private Tower mInsertedTower;
    private Plateau mCurrentPlateau;

    public TowerInserter(GameEngine gameEngine, ScoreBoard scoreBoard, TowerSelector towerSelector) {
        mGameEngine = gameEngine;
        mTowerSelector = towerSelector;
        mScoreBoard = scoreBoard;
    }

    public void insertTower(Tower tower) {
        if (mGameEngine.isThreadChangeNeeded()) {
            final Tower finalTower = tower;
            mGameEngine.post(new Runnable() {
                @Override
                public void run() {
                    insertTower(finalTower);
                }
            });
            return;
        }

        if (mInsertedTower == null) {
            mInsertedTower = tower;
            mGameEngine.add(tower);
            mTowerSelector.selectTower(mInsertedTower);
        }
    }

    public void setPosition(Vector2 position) {
        if (mGameEngine.isThreadChangeNeeded()) {
            final Vector2 finalPosition = position;
            mGameEngine.post(new Runnable() {
                @Override
                public void run() {
                    setPosition(finalPosition);
                }
            });
            return;
        }

        if (mInsertedTower != null) {
            Plateau closestPlateau = mGameEngine.get(Types.PLATEAU)
                    .cast(Plateau.class)
                    .filter(Plateau.unoccupied())
                    .min(Entity.distanceTo(position));

            if (closestPlateau != null) {
                mCurrentPlateau = closestPlateau;
                mInsertedTower.setPosition(mCurrentPlateau.getPosition());
            } else {
                cancel();
            }
        }
    }

    public void buyTower() {
        if (mGameEngine.isThreadChangeNeeded()) {
            mGameEngine.post(new Runnable() {
                @Override
                public void run() {
                    buyTower();
                }
            });
            return;
        }

        if (mInsertedTower != null) {
            mInsertedTower.setEnabled(true);
            mCurrentPlateau.setOccupant(mInsertedTower);
            mScoreBoard.takeCredits(mInsertedTower.getValue());
            mTowerSelector.selectTower(null);

            mCurrentPlateau = null;
            mInsertedTower = null;
        }
    }

    public void cancel() {
        if (mGameEngine.isThreadChangeNeeded()) {
            mGameEngine.post(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            });
            return;
        }

        if (mInsertedTower != null) {
            mGameEngine.remove(mInsertedTower);

            mCurrentPlateau = null;
            mInsertedTower = null;
        }
    }

}