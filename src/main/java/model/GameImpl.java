package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.api.Board;
import model.api.Cell;
import model.api.Cell.CellType;
import model.api.Game;
import model.api.Item;
import model.api.Player;
import model.api.Player.PlayerType;
import utils.BColor;
import model.api.Shop;

/**
 * Game Implementation class.
 */
public final class GameImpl implements Game {

    private final Board board;
    private final Player humanPlayer;
    private final List<Player> players;
    private final Turn turn;
    private final Shop shop;

    /**
     * Constructor.
     * 
     * @param playerName    the player name
     * @param playersNumber the number of players
     */
    public GameImpl(final String playerName, final int playersNumber) {

        this.board = new BoardImpl();

        // add players
        this.humanPlayer = new PlayerImpl(playerName, PlayerType.HUMAN,
                BColor.BLUE, CellType.BOTTOM_LEFT_HOUSE, board.getBottomLeftSafePath(), board.getBottomLeftPawnsStartPos());
        final Player p1 = new PlayerImpl("Player 2", PlayerType.COMPUTER,
                BColor.GREEN, CellType.TOP_RIGHT_HOUSE, board.getTopRightSafePath(), board.getTopRightPawnsStartPos());
        this.players = new ArrayList<>(List.of(this.humanPlayer, p1));

        if (playersNumber > players.size()) {
            final Player p2 = new PlayerImpl("Player 3", PlayerType.COMPUTER,
                    BColor.RED, CellType.TOP_LEFT_HOUSE, board.getTopLeftSafePath(), board.getTopLeftPawnsStartPos());
            final Player p3 = new PlayerImpl("Player 4", PlayerType.COMPUTER,
                    BColor.YELLOW, CellType.BOTTOM_RIGHT_HOUSE, board.getBottomRighSafePath(), 
                    board.getBottomRightPawnsStartPos());
            this.players.add(p2);
            this.players.add(p3);
        }

        // create turn
        turn = new Turn(this.humanPlayer);
        shop = new ShopImpl();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Set<Cell> getBoardCells() {
        return this.getBoard().getCells();
    }

    @Override
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    @Override
    public Player getHumanPlayer() {
        return this.humanPlayer;
    }

    @Override
    public Turn getTurn() {
        return this.turn;
    }

    @Override
    public Map<Integer, Item> getShowcase() {
        return this.shop.getShowcase();
    }

    @Override
    public void buyItem(final Player player, final Item item) {
       this.shop.sellItem((PlayerImpl) player, item);
    }

    @Override
    public boolean isOver(final Result res) {
        return res == Result.WIN;
    }

    @Override
    public Shop getShop() {
        return this.shop;
    }

}
