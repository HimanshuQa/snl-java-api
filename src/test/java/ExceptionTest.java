import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

public class ExceptionTest {

	GameInProgressException gameinboard;
	InvalidTurnException invalidturn;
	MaxPlayersReachedExeption maxplayer;
	NoUserWithSuchUUIDException nouser;
	PlayerExistsException playerexist;
	Board board;
	UUID uid;
	
	@BeforeClass
	public void initialise() {
		
		gameinboard = new GameInProgressException();
		maxplayer = new MaxPlayersReachedExeption(5);
		nouser = new NoUserWithSuchUUIDException("any_uuid");
		playerexist = new PlayerExistsException("any_name");
		this.uid = UUID.randomUUID();
		invalidturn = new InvalidTurnException(uid);


		//		invalidturn = new InvalidTurnException(uid);
	}

	
	@Test
	public void test_GameInProgressException() {
		Assert.assertEquals("New player cannot join since the game has started", gameinboard.getMessage());
	}
	
	@Test
	public void test_maxPlayersReachedException() {
		Assert.assertEquals("The board already has maximum allowed Player: " + 5,maxplayer.getMessage());

	}
	
	@Test
	public void test_NoUserWithSuchUUIDException() {
		
		Assert.assertEquals("No Player with uuid '"+"any_uuid"+"' on board",nouser.getMessage());

	}
	
	@Test
	public void test_PlayerExistsException(){
		Assert.assertEquals("Player '"+"any_name"+"' already exists on board",playerexist.getMessage());
	}
	
	@Test
	public void test_InvalidTurnException(){

		Assert.assertEquals("Player '"+uid+"' does not have the turn",invalidturn.getMessage());
	}
	
}
