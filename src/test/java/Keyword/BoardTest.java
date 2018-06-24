package Keyword;


import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.testng.annotations.Test;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.Assert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HIM
 */
public class BoardTest {

    Board board;

    public BoardTest() throws UnsupportedEncodingException, IOException {
        board = new Board();
    }

    public JSONObject roll_dice_without_registering_any_user() throws InvalidTurnException, FileNotFoundException, UnsupportedEncodingException {
        return board.rollDice(null);

    }

    public JSONArray delete_user_without_registering_anybody() throws NoUserWithSuchUUIDException, FileNotFoundException, UnsupportedEncodingException {
        return board.deletePlayer(UUID.randomUUID());
    }

    public void add_user(String name, int no_of_user, Boolean change_name) throws PlayerExistsException, GameInProgressException, UnsupportedEncodingException, MaxPlayersReachedExeption, IOException {
        int before_player_count, after_player_count;
        if (change_name) {
            for (int i = 0; i < no_of_user; i++) {
                before_player_count = board.getData().getJSONArray("players").length();
                board.registerPlayer(name + i);
                after_player_count = board.getData().getJSONArray("players").length();
                Assert.assertEquals(board.getData().getJSONArray("players").getJSONObject(i).getInt("position"), 0, "position of new player isnot 0");
                Assert.assertEquals(board.getData().getJSONArray("players").getJSONObject(i).getString("name"), name + i, "name of the user registered is not same");

                if (after_player_count < before_player_count) {
                    Assert.fail("player count does not increase in JSON");
                }
            }
        } else {
            for (int i = 0; i < no_of_user; i++) {
                before_player_count = board.getData().getJSONArray("players").length();
                board.registerPlayer(name);
                Assert.assertEquals(board.getData().getJSONArray("players").getJSONObject(i).getInt("position"), 0, "position of new player is not 0");
                Assert.assertEquals(board.getData().getJSONArray("players").getJSONObject(i).getString("name"), name, "name of the user registered is not same");

                after_player_count = board.getData().getJSONArray("players").length();
                if (after_player_count < before_player_count) {
                    Assert.fail("player count does not increase in JSON");
                }
            }
        }
    }

    public void add_more_than_four_user() throws PlayerExistsException, GameInProgressException, UnsupportedEncodingException, MaxPlayersReachedExeption, IOException {

        String name = "any_name";
        add_user(name, 6, Boolean.TRUE);

    }

    public void add_same_name_user() throws PlayerExistsException, GameInProgressException, UnsupportedEncodingException, MaxPlayersReachedExeption, IOException {

        String name = "any_name";
        add_user(name, 5, Boolean.FALSE);
    }

    public void register_user_after_starting_game() {
        String name = "any_name";
        UUID uid;
        try {

            add_user(name, 2, Boolean.TRUE);
            for (int i = 0; i < 2; i++) {

                uid = (UUID) board.getData().getJSONArray("players").getJSONObject(i).get("uuid");
                board.rollDice(uid);
                board.registerPlayer("player");

            }
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), new GameInProgressException().getClass());
        }
    }

    public void delete_user_which_does_not_exist() {
        String name = "any_name";
        UUID uid;
        try {

            add_user(name, 4, Boolean.TRUE);
            uid = UUID.randomUUID();
            board.deletePlayer(uid);

        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), new NoUserWithSuchUUIDException("any uid").getClass());
        }
    }

    public void check_user_is_deleted() {
        String name = "any_name";
        UUID uid;
        try {
            add_user(name, 4, Boolean.TRUE);
            uid = (UUID) board.getData().getJSONArray("players").getJSONObject(0).get("uuid");
            board.deletePlayer(uid);
            Assert.assertEquals(board.getData().getJSONArray("players").length(), 3);
            board.deletePlayer(uid);

        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), new NoUserWithSuchUUIDException("any uid").getClass());
        }
    }

    public void Wrong_turn_of_user() {
        String name = "any_name";
        UUID uid = null;
        try {
            add_user(name, 4, Boolean.TRUE);
            //getting the uuid of second player instead of first player
            uid = (UUID) board.getData().getJSONArray("players").getJSONObject(1).get("uuid");
            board.rollDice(uid);

        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), new InvalidTurnException(uid).getClass());
        }
    }

    public void check_player_position_moved_or_not_after_dice_is_rolled() {
        String name = "any_name";
        UUID uid = null;
        try {
            add_user(name, 4, Boolean.TRUE);
            int before_pos = board.getData().getJSONArray("players").getJSONObject(0).getInt("position");
            uid = (UUID) board.getData().getJSONArray("players").getJSONObject(0).get("uuid");
            int dice = board.rollDice(uid).getInt("dice");
            Assert.assertEquals(board.getData().getInt("turn"), 1, "Turn is not updated");
            int after_pos = board.getData().getJSONArray("players").getJSONObject(0).getInt("position");
            Assert.assertEquals(after_pos, before_pos + dice, "Positon of player is not what is need to be");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
