package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import model.PlayerHome.HomePosition;
import model.api.Dice;
import model.api.Item;
import model.api.Pawn;
import model.api.Player;
import model.api.Wallet;

/**
 * Player Implementation class.
 */
@SuppressWarnings("all")
public final class PlayerImpl implements Player {

    private final String name;
    private final PlayerType type;
    private final Color color;
    private final HomePosition homePosition;
    // private final List<Pair<Integer,Integer>> safePath;
    private final List<Pawn> pawns;
    private int coins;
    private boolean isPlayerTurn;
    private Dice dice;
    private InventoryImpl inventory = new InventoryImpl();
    private List<Item> itemApplied = new ArrayList<>();

    /**
     * Player constructor.
     * 
     * @param name
     *               the player name
     * @param type
     *               the player type
     * @param color
     *               the player color
     * @param homePosition
     *               the position of the player's house
     */
    public PlayerImpl(final String name, final PlayerType type,
            final Color color, final HomePosition homePosition) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.homePosition = homePosition;

        this.pawns = new ArrayList<>();
        for (int i = 0; i < homePosition.getPawnPositions().size(); i++) {
            pawns.add(new PawnImpl(homePosition.getPawnPositions().get(i), i, homePosition, color));
        }

        this.coins = 0;
        this.isPlayerTurn = false;
        this.dice = new BasicDiceImpl();
    }

    // getters

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerType getType() {
        return type;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public HomePosition getHomePosition() {
        return homePosition;
    }

    @Override
    public List<Pawn> getPawns() {
        return List.copyOf(pawns);
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    @Override
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    @Override
    public Dice getDice() {
        return this.dice;
    }


    @Override
    public String toString() {
        return "PlayerImpl [name=" + name + ", type=" + type + ", color=" + color + ", homePosition=" + homePosition
                + ", pawns=" + pawns + ", coins=" + coins + ", isPlayerTurn=" + isPlayerTurn + "]";
    }

    @Override
    public int rollDice() {
        return this.getDice().roll();
    }

    @Override
    public Wallet getWallet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWallet'");
    }

    @Override
    public InventoryImpl getPlayerInventory() { //
        InventoryImpl inventoryCopy = new InventoryImpl();
        for (Entry<Integer, Item> entry : inventory.getInventory().entrySet()) {
            inventoryCopy.getInventory().put(entry.getKey(), entry.getValue());
        }
        return inventoryCopy;
    }

    @Override
    public void modifyCoins(final int value) { //
        this.coins = this.coins + value;
    }

    @Override
    public void addItem(final Integer id, final Item item) { //
        inventory.getInventory().put(id, item);
    } 

    @Override
    public void itemApplied(final Item item) { //
        itemApplied.add(item);
    } 

    @Override
    public void useItem(final Item item, final PlayerImpl player) { //
        inventory.getInventory().remove(item.getId(), item);
        player.itemApplied(item);
    } 

    @Override
    public void malusExpired() { 
        for (Item i : itemApplied) { 
            if (!i.isBonus()) {
                itemApplied.remove(i); 
            }
        }
    }

    @Override
    public void bonusExpired() { 
        for (Item i : itemApplied) { 
            if (i.isBonus()) {
                itemApplied.remove(i); 
            }
        }
    }

}
