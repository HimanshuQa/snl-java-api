
import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        return board.deletePlayer(null);
    }

    public void add_user(String name, Boolean change_name) throws PlayerExistsException, GameInProgressException, UnsupportedEncodingException, MaxPlayersReachedExeption, IOException {
        int before_player_count, after_player_count;
        if (change_name) {
            for (int i = 0; i < 6; i++) {
                before_player_count = board.getData().getJSONArray("players").length();
                board.registerPlayer(name + i);
                after_player_count = board.getData().getJSONArray("players").length();
                if (after_player_count < before_player_count) {
                    Assert.assertTrue(false, "player count does not increase in JSON");
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                before_player_count = board.getData().getJSONArray("players").length();
                board.registerPlayer(name);
                after_player_count = board.getData().getJSONArray("players").length();
                if (after_player_count < before_player_count) {
                    Assert.assertTrue(false, "player count does not increase in JSON");
                }
            }
        }
    }

    public void add_more_than_four_user() throws PlayerExistsException, GameInProgressException, UnsupportedEncodingException, MaxPlayersReachedExeption, IOException {

        String name = "any_name";
        add_user(name, Boolean.TRUE);

    }

    public void add_same_name_user() throws PlayerExistsException, GameInProgressException, UnsupportedEncodingException, MaxPlayersReachedExeption, IOException {

        String name = "any_name";
        add_user(name, Boolean.FALSE);
    }

}
