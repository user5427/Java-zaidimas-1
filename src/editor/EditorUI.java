package editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import editor.FileManagers.FileChooser;
import editor.editorUI_elements.Button;
import editor.editorUI_elements.ImageSlider;
import editor.editorUI_elements.MouseUser;
import editor.editorUI_elements.Slider;
import gamestates.Gamestate;
import levels.LevelData;
import main.Game;
import utils.LoadSave;

import static utils.Constants.EditorUI.*;

/**
 * The EditorUI class represents the user interface for the level editor.
 * It implements the MouseUser interface to handle mouse-related events.
 */
public class EditorUI implements MouseUser {
    private BufferedImage editorBannder, tileSelection;
    private ImageSlider imageSlider;

    private BufferedImage levelAtlas, tileImgs[];
    private int levelAtlasWidthTiles, levelAtlasHeightTiles;
    private int tileCount;
    private int selectedTile;

    private boolean mouseInUse;

    private LevelData levelData;

    private Rectangle xSlider;
    private Slider xSizeSlider;
    private Rectangle ySlider;
    private Slider ySizeSlider;

    private float moveSpeed = DEFAULT_SPEED * Game.SCALE;
    private boolean left, up, right, down, scaleDown, scaleUp;
    private Point focusPoint;
    private float focusScale = 1f;

    private Button filesButton;
    private Button menuButton;

    private FileChooser fileChooser;

    /**
     * Constructor for the EditorUI class.
     * Initializes various UI elements, including buttons, sliders, and file chooser.
     */
    public EditorUI() {
        levelAtlas = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        editorBannder = LoadSave.GetSpriteAtlas(LoadSave.EDITOR_BANNER);
        tileSelection = LoadSave.GetSpriteAtlas(LoadSave.TILE_SELECTION);
        getTileImgs();
        imageSlider = new ImageSlider(tileImgs, tileCount, this);
        initSliders();
        initButtons();
        fileChooser = new FileChooser();
        focusPoint = new Point(0, 0);
    }

    private void initButtons() {
        filesButton = new Button((int) (10 * Game.SCALE), (int) (70 * Game.SCALE), BUTTON.WIDHT, BUTTON.HEIGHT, 3, LoadSave.FILES_BUTTON);
        menuButton = new Button((int) (10 * Game.SCALE), (int) (90 * Game.SCALE), BUTTON.WIDHT, BUTTON.HEIGHT, 3, LoadSave.MENU_BUTTON);
    }

    private void initSliders() {
        xSlider = new Rectangle();
        xSlider.x = (int) (50 * Game.SCALE);
        xSlider.y = (int) (348 * Game.SCALE);
        xSizeSlider = new Slider(xSlider.x, xSlider.y, SLIDER_WIDTH, SLIDE_BUTTON_HEIGHT, this);
        ySlider = new Rectangle();
        ySlider.x = (int) (50 * Game.SCALE);
        ySlider.y = (int) (372 * Game.SCALE);
        ySizeSlider = new Slider(ySlider.x, ySlider.y, SLIDER_WIDTH, SLIDE_BUTTON_HEIGHT, this);
    }

    private void getTileImgs() {
        levelAtlasWidthTiles = levelAtlas.getWidth() / Game.TILES_DEFAULT_SIZE;
        levelAtlasHeightTiles = levelAtlas.getHeight() / Game.TILES_DEFAULT_SIZE;
        tileCount = levelAtlasWidthTiles * levelAtlasHeightTiles;
        tileImgs = new BufferedImage[tileCount];
        for (int i = 0; i < levelAtlasHeightTiles; i++)
            for (int j = 0; j < levelAtlasWidthTiles; j++)
                tileImgs[i * levelAtlasWidthTiles + j] = levelAtlas.getSubimage(j * Game.TILES_DEFAULT_SIZE, i * Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);

    }

    /**
     * Updates the state of the editor UI, including sliders, buttons, and file chooser.
     */
    public void update() {
        updateScale();
        updateFocusPoint();
        imageSlider.update();
        xSizeSlider.update();
        ySizeSlider.update();
        filesButton.update();
        menuButton.update();

    }

    private void updateScale() {
        if (!(scaleUp && scaleDown)) {
            if (scaleUp && focusScale + DEFAULT_SCALE_SPEED < MAX_SCALE)
                focusScale += DEFAULT_SCALE_SPEED;
            else if (scaleDown && focusScale - DEFAULT_SCALE_SPEED > MIN_SCALE)
                focusScale -= DEFAULT_SCALE_SPEED;
        }

        //moveSpeed = DEFAULT_SPEED * Game.SCALE * focusScale;

    }

    private void updateFocusPoint() {
        if (!(left && right)) {
            if (left)
                focusPoint.x += moveSpeed;
            else if (right)
                focusPoint.x -= moveSpeed;
        }

        if (!(up && down)) {
            if (up)
                focusPoint.y += moveSpeed;
            else if (down)
                focusPoint.y -= moveSpeed;
        }
    }

    /**
     * Draws the editor UI, including the world display, banners, and buttons.
     *
     * @param g The Graphics object to draw on.
     */
    public void draw(Graphics g) {
        displayWorld(g);

        g.drawImage(editorBannder, (int) (Game.GAME_WIDTH / 2 - BANNER_WIDTH / 2), (int) (10 * Game.SCALE), BANNER_WIDTH, BANNER_HEIGHT, null);
        g.drawImage(tileSelection, (int) (Game.GAME_WIDTH / 2 + IMAGE_BAR_WIDTH / 2), (int) (317 * Game.SCALE), SELECTION_DISPLAY_WIDTH, SELECTION_DISPLAY_HEIGHT, null);
        g.drawImage(tileImgs[selectedTile], (int) (Game.GAME_WIDTH / 2 + IMAGE_BAR_WIDTH / 2 + 12 * Game.SCALE), (int) (317 * Game.SCALE + 40 * Game.SCALE), Game.TILES_SIZE, Game.TILES_SIZE, null);


        imageSlider.draw(g);
        xSizeSlider.draw(g);
        ySizeSlider.draw(g);
        filesButton.draw(g);
        menuButton.draw(g);
    }

    private void displayWorld(Graphics g) {
        if (levelData == null)
            return;
        for (int i = 0; i < levelData.getySize(); i++)
            for (int j = 0; j < levelData.getxSize(); j++) {
                if (levelData.getValueAtPoint(j, i) < tileImgs.length)
                    g.drawImage(tileImgs[levelData.getValueAtPoint(j, i)], (int) ((j * Game.TILES_SIZE + (int) focusPoint.x) * focusScale), (int) ((i * Game.TILES_SIZE + (int) focusPoint.y) * focusScale), (int) (Game.TILES_SIZE * focusScale), (int) (Game.TILES_SIZE * focusScale), null);
            }

        g.setColor(Color.pink);
        for (int i = 0; i < levelData.getySize(); i++)
            for (int j = 0; j < levelData.getxSize(); j++)
                g.drawRect((int) ((j * Game.TILES_SIZE + (int) focusPoint.x) * focusScale), (int) ((i * Game.TILES_SIZE + (int) focusPoint.y) * focusScale), (int) (Game.TILES_SIZE * focusScale), (int) (Game.TILES_SIZE * focusScale));

    }

    /**
     * Handles mouse press events for various UI elements.
     *
     * @param e The MouseEvent object representing the mouse press event.
     */
    public void mousePressed(MouseEvent e) {
        imageSlider.mousePressed(e);
        xSizeSlider.mousePressed(e);
        ySizeSlider.mousePressed(e);
        filesButton.mousePressed(e);
        menuButton.mousePressed(e);
    }

    /**
     * Handles mouse move events for various UI elements.
     *
     * @param e The MouseEvent object representing the mouse move event.
     */
    public void mouseMoved(MouseEvent e) {
        imageSlider.mouseMoved(e);
        xSizeSlider.mouseMoved(e);
        ySizeSlider.mouseMoved(e);
        filesButton.mouseMoved(e);
        menuButton.mouseMoved(e);
    }

    /**
     * Handles mouse release events for various UI elements.
     *
     * @param e The MouseEvent object representing the mouse release event.
     */
    public void mouseReleased(MouseEvent e) {
        imageSlider.mouseReleased(e);
        xSizeSlider.mouseReleased(e);
        ySizeSlider.mouseReleased(e);
        filesButton.mouseReleased(e);
        menuButton.mouseReleased(e);

        if (menuButton.isTriggered()) {
            menuButton.resetTriggered();
            Gamestate.state = Gamestate.MENU;
        } else if (filesButton.isTriggered()) {
            filesButton.resetTriggered();
            fileChooser.openMenu();
        }

    }

    /**
     * Handles mouse drag events for various UI elements.
     *
     * @param e The MouseEvent object representing the mouse drag event.
     */
    public void mouseDragged(MouseEvent e) {
        imageSlider.mouseDragged(e);
        xSizeSlider.mouseDragged(e);
        ySizeSlider.mouseDragged(e);
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public boolean getMouseInUse() {
        return mouseInUse;
    }

    @Override
    public void setMouseInUse(boolean mouseInUse) {
        this.mouseInUse = mouseInUse;
    }

    public int getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(int selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void setWorld(LevelData levelData) {
        this.levelData = levelData;

    }

    public float getXSize() {
        return xSizeSlider.getValue();
    }

    public float getYSize() {
        return ySizeSlider.getValue();
    }


    public Point getFocusPoint() {
        return focusPoint;
    }

    public float getScale() {
        return focusScale;
    }

    /**
     * Handles key press events for various UI elements.
     *
     * @param e The KeyEvent object representing the key press event.
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_E:
                scaleUp = true;
                break;
            case KeyEvent.VK_Q:
                scaleDown = true;
                break;
            default:
                break;
        }

    }

    /**
     * Handles key release events for various UI elements.
     *
     * @param e The KeyEvent object representing the key release event.
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_E:
                scaleUp = false;
                break;
            case KeyEvent.VK_Q:
                scaleDown = false;
                break;
            default:
                break;
        }
    }

}
