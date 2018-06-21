/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author HIM
 */
public class Alltest {

    BoardTest boardtest;

    public Alltest() throws IOException {
    }

    @Test(expectedExceptions = {JSONException.class})
    public void test_roll_dice_without_registering_any_user() throws InvalidTurnException, FileNotFoundException, UnsupportedEncodingException {
        boardtest.roll_dice_without_registering_any_user();
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void test_delete_user_without_registering_anybody() throws NoUserWithSuchUUIDException, FileNotFoundException, UnsupportedEncodingException {
        boardtest.delete_user_without_registering_anybody();
    }

    @Test(expectedExceptions = {MaxPlayersReachedExeption.class})
    public void test_add_more_than_four_user() throws PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
        boardtest.add_more_than_four_user();
    }

    @Test(expectedExceptions = {PlayerExistsException.class})
    public void test_add_same_name_user() throws PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException {
        boardtest.add_same_name_user();
    }
    
    
    
    @AfterMethod
    public void teardown() {
        boardtest = null;
        System.gc();
    }
    
    @BeforeMethod
    public void createobject() throws IOException
    {
        boardtest = new BoardTest();
    }
    
    
}
