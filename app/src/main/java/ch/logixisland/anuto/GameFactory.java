package ch.logixisland.anuto;

import android.content.Context;

import ch.logixisland.anuto.business.control.TowerControl;
import ch.logixisland.anuto.business.control.TowerInserter;
import ch.logixisland.anuto.business.control.TowerSelector;
import ch.logixisland.anuto.business.level.GameSpeedManager;
import ch.logixisland.anuto.business.level.LevelLoader;
import ch.logixisland.anuto.business.level.TowerAging;
import ch.logixisland.anuto.business.level.WaveManager;
import ch.logixisland.anuto.business.manager.GameManager;
import ch.logixisland.anuto.business.manager.SettingsManager;
import ch.logixisland.anuto.business.score.ScoreBoard;
import ch.logixisland.anuto.engine.logic.GameEngine;
import ch.logixisland.anuto.engine.render.Renderer;
import ch.logixisland.anuto.engine.render.Viewport;
import ch.logixisland.anuto.engine.render.shape.ShapeFactory;
import ch.logixisland.anuto.engine.render.sprite.SpriteFactory;
import ch.logixisland.anuto.engine.sound.SoundFactory;
import ch.logixisland.anuto.engine.sound.SoundManager;
import ch.logixisland.anuto.engine.theme.ThemeManager;
import ch.logixisland.anuto.entity.enemy.EnemyFactory;
import ch.logixisland.anuto.entity.plateau.PlateauFactory;
import ch.logixisland.anuto.entity.tower.TowerFactory;
import ch.logixisland.anuto.view.game.BackButtonControl;

public class GameFactory {

    // Engine
    private final ThemeManager mThemeManager;
    private final SoundManager mSoundManager;
    private final SpriteFactory mSpriteFactory;
    private final ShapeFactory mShapeFactory;
    private final SoundFactory mSoundFactory;
    private final Viewport mViewport;
    private final Renderer mRenderer;
    private final GameEngine mGameEngine;
    private final SettingsManager mSettingsManager;

    // Entity
    private final PlateauFactory mPlateauFactory;
    private final TowerFactory mTowerFactory;
    private final EnemyFactory mEnemyFactory;

    // Business
    private final ScoreBoard mScoreBoard;
    private final TowerSelector mTowerSelector;
    private final TowerControl mTowerControl;
    private final TowerAging mTowerAging;
    private final TowerInserter mTowerInserter;
    private final LevelLoader mLevelLoader;
    private final WaveManager mWaveManager;
    private final GameSpeedManager mSpeedManager;
    private final GameManager mGameManager;

    // View
    private final BackButtonControl mBackButtonControl;

    public GameFactory(Context context) {
        // Engine
        mSettingsManager = new SettingsManager(context);
        mThemeManager = new ThemeManager(context, mSettingsManager);
        mSoundManager = new SoundManager(context);
        mSpriteFactory = new SpriteFactory(context, mThemeManager);
        mShapeFactory = new ShapeFactory(mThemeManager);
        mSoundFactory = new SoundFactory(context, mSoundManager);
        mViewport = new Viewport();
        mRenderer = new Renderer(mViewport, mThemeManager);
        mGameEngine = new GameEngine(mRenderer);

        // Entity
        mPlateauFactory = new PlateauFactory();
        mTowerFactory = new TowerFactory();
        mEnemyFactory = new EnemyFactory();

        // Business
        mScoreBoard = new ScoreBoard();
        mLevelLoader = new LevelLoader(context, mGameEngine, mViewport, mScoreBoard, mPlateauFactory, mTowerFactory, mEnemyFactory);
        mWaveManager = new WaveManager(mGameEngine, mScoreBoard, mLevelLoader, mEnemyFactory);
        mSpeedManager = new GameSpeedManager(mGameEngine);
        mTowerAging = new TowerAging(mGameEngine, mWaveManager, mLevelLoader);
        mGameManager = new GameManager(mGameEngine, mScoreBoard, mLevelLoader, mWaveManager, mSpeedManager);
        mTowerSelector = new TowerSelector(mGameEngine, mGameManager, mScoreBoard);
        mTowerControl = new TowerControl(mGameEngine, mScoreBoard, mTowerSelector, mTowerFactory);
        mTowerInserter = new TowerInserter(mGameEngine, mGameManager, mTowerFactory, mTowerSelector, mTowerAging, mScoreBoard);

        // View
        mBackButtonControl = new BackButtonControl(mSettingsManager);

        mGameManager.restart();
    }

    public ThemeManager getThemeManager() {
        return mThemeManager;
    }

    public SoundManager getSoundManager() {
        return mSoundManager;
    }

    public SpriteFactory getSpriteFactory() {
        return mSpriteFactory;
    }

    public ShapeFactory getShapeFactory() {
        return mShapeFactory;
    }

    public SoundFactory getSoundFactory() {
        return mSoundFactory;
    }

    public Viewport getViewport() {
        return mViewport;
    }

    public Renderer getRenderer() {
        return mRenderer;
    }

    public GameEngine getGameEngine() {
        return mGameEngine;
    }

    public TowerFactory getTowerFactory() {
        return mTowerFactory;
    }

    public ScoreBoard getScoreBoard() {
        return mScoreBoard;
    }

    public TowerSelector getTowerSelector() {
        return mTowerSelector;
    }

    public TowerControl getTowerControl() {
        return mTowerControl;
    }

    public TowerInserter getTowerInserter() {
        return mTowerInserter;
    }

    public LevelLoader getLevelLoader() {
        return mLevelLoader;
    }

    public WaveManager getWaveManager() {
        return mWaveManager;
    }

    public GameSpeedManager getSpeedManager() {
        return mSpeedManager;
    }

    public GameManager getGameManager() {
        return mGameManager;
    }

    public SettingsManager getSettingsManager() {
        return mSettingsManager;
    }

    public BackButtonControl getBackButtonControl() {
        return mBackButtonControl;
    }
}
