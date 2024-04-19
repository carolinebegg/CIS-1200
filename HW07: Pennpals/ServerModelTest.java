package org.cis1200;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Collection;
import java.util.Set;

public class ServerModelTest {
    private ServerModel model;

    /**
     * Before each test, we initialize model to be
     * a new ServerModel (with all new, empty state)
     */
    @BeforeEach
    public void setUp() {
        // We initialize a fresh ServerModel for each test
        model = new ServerModel();
    }

    /**
     * Here is an example test that checks the functionality of your
     * changeNickname error handling. Each line has commentary directly above
     * it which you can use as a framework for the remainder of your tests.
     */
    @Test
    public void testInvalidNickname() {
        // A user must be registered before their nickname can be changed,
        // so we first register a user with an arbitrarily chosen id of 0.
        model.registerUser(0);

        // We manually create a Command that appropriately tests the case
        // we are checking. In this case, we create a NicknameCommand whose
        // new Nickname is invalid.
        Command command = new NicknameCommand(0, "User0", "!nv@l!d!");

        // We manually create the expected Broadcast using the Broadcast
        // factory methods. In this case, we create an error Broadcast with
        // our command and an INVALID_NAME error.
        Broadcast expected = Broadcast.error(
                command, ServerResponse.INVALID_NAME
        );

        // We then get the actual Broadcast returned by the method we are
        // trying to test. In this case, we use the updateServerModel method
        // of the NicknameCommand.
        Broadcast actual = command.updateServerModel(model);

        // The first assertEquals call tests whether the method returns
        // the appropriate Broadcast.
        assertEquals(expected, actual, "Broadcast");

        // We also want to test whether the state has been correctly
        // changed.In this case, the state that would be affected is
        // the user's Collection.
        Collection<String> users = model.getRegisteredUsers();

        // We now check to see if our command updated the state
        // appropriately. In this case, we first ensure that no
        // additional users have been added.
        assertEquals(1, users.size(), "Number of registered users");

        // We then check if the username was updated to an invalid value
        // (it should not have been).
        assertTrue(users.contains("User0"), "Old nickname still registered");

        // Finally, we check that the id 0 is still associated with the old,
        // unchanged nickname.
        assertEquals(
                "User0", model.getNickname(0),
                "User with id 0 nickname unchanged"
        );
    }

    /*
     * Your TAs will be manually grading the tests that you write below this
     * comment block. Don't forget to test the public methods you have added to
     * your ServerModel class, as well as the behavior of the server in
     * different scenarios.
     * You might find it helpful to take a look at the tests we have already
     * provided you with in Task4Test, Task3Test, and Task5Test.
     */

    /** Task 3 Tests **/

    @Test
    public void testDeregisterOwnerOfEmptyChannel() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        model.deregisterUser(0);
        assertTrue(model.getUsersInChannel("java").isEmpty(), "No registered users");
        assertFalse(model.getChannels().contains("java"));
    }

    @Test
    public void testDeregisterOwnerOfFullChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command join = new JoinCommand(1, "User1", "java");
        join.updateServerModel(model);
        model.deregisterUser(0);
        assertTrue(model.getUsersInChannel("java").isEmpty(), "No registered users");
        assertFalse(model.getChannels().contains("java"));
    }

    @Test
    public void testDeregisterOwnerOfMultipleChannel() {
        model.registerUser(0);
        model.registerUser(1);
        model.registerUser(2);
        Command chanOne = new CreateCommand(0, "User0", "java", false);
        chanOne.updateServerModel(model);
        Command chanTwo = new CreateCommand(0, "User0", "mocha", false);
        chanTwo.updateServerModel(model);
        Command chanThree = new CreateCommand(0, "User0", "coffee", true);
        chanThree.updateServerModel(model);
        Command joinOne = new JoinCommand(1, "User1", "java");
        joinOne.updateServerModel(model);
        Command joinTwo = new JoinCommand(2, "User2", "mocha");
        joinTwo.updateServerModel(model);
        model.deregisterUser(0);
        assertTrue(model.getUsersInChannel("java").isEmpty(), "No registered users");
        assertTrue(model.getUsersInChannel("mocha").isEmpty(), "No registered users");
        assertTrue(model.getUsersInChannel("mocha").isEmpty(), "No registered users");
        assertFalse(model.getChannels().contains("java"));
        assertFalse(model.getChannels().contains("mocha"));
        assertFalse(model.getChannels().contains("coffee"));
    }

    @Test
    public void testDeregisterUserInChannel() {
        model.registerUser(0);
        model.registerUser(1);
        model.registerUser(2);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command joinOne = new JoinCommand(1, "User1", "java");
        joinOne.updateServerModel(model);
        Command joinTwo = new JoinCommand(2, "User2", "java");
        joinTwo.updateServerModel(model);
        model.deregisterUser(1);
        assertFalse(model.getUsersInChannel("java").isEmpty(), "No affect on other users");
        assertTrue(model.getChannels().contains("java"));
        assertTrue(model.getUsersInChannel("java").contains("User0"));
        assertTrue(model.getUsersInChannel("java").contains("User2"));
    }

    @Test
    public void testChangeNickNameOfOwner() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "cis120", false);
        create.updateServerModel(model);
        Command command = new NicknameCommand(0, "User0", "caroline");
        Set<String> owner = Collections.singleton("caroline");
        Broadcast expected = Broadcast.okay(command, owner);
        assertEquals(expected, command.updateServerModel(model), "Broadcast");
        Collection<String> users = model.getRegisteredUsers();
        assertFalse(users.contains("User0"), "Old nick not registered");
        assertTrue(users.contains("caroline"), "New nick registered");
        assertEquals("caroline", model.getOwner("cis120"));
    }

    /** Task 4 Tests **/
    @Test
    public void testCreateChannelAlreadyExists() {
        model.registerUser(0);
        model.registerUser(1);
        Command chanOne = new CreateCommand(0, "User0", "java", false);
        chanOne.updateServerModel(model);
        Command chanTwo = new CreateCommand(1, "User1", "java", false);
        Broadcast expected = Broadcast.error(chanTwo, ServerResponse.CHANNEL_ALREADY_EXISTS);
        Broadcast actual = chanTwo.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testCreateChannelInvalidName() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "!nv@l!d!", false);
        Broadcast expected = Broadcast.error(create, ServerResponse.INVALID_NAME);
        Broadcast actual = create.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testJoinChannelDoesNotExist() {
        model.registerUser(0);
        Command join = new JoinCommand(0, "User0", "java");
        Broadcast expected = Broadcast.error(join, ServerResponse.NO_SUCH_CHANNEL);
        Broadcast actual = join.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testJoinPrivateChannel() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command join = new JoinCommand(0, "User0", "java");
        Broadcast expected = Broadcast.error(join, ServerResponse.JOIN_PRIVATE_CHANNEL);
        Broadcast actual = join.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testMessageChannelDoesNotExists() {
        model.registerUser(0);
        Command message = new MessageCommand(0, "User0", "java", "hi");
        Broadcast expected = Broadcast.error(message, ServerResponse.NO_SUCH_CHANNEL);
        Broadcast actual = message.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testMessageMemberNotInChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command message = new MessageCommand(1, "User0", "java", "hi");
        Broadcast expected = Broadcast.error(message, ServerResponse.USER_NOT_IN_CHANNEL);
        Broadcast actual = message.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testLeaveChannelDoesNotExist() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command leave = new LeaveCommand(1, "User0", "cis");
        Broadcast expected = Broadcast.error(leave, ServerResponse.NO_SUCH_CHANNEL);
        Broadcast actual = leave.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testLeaveUserNotInChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command leave = new LeaveCommand(1, "User1", "java");
        Broadcast expected = Broadcast.error(leave, ServerResponse.USER_NOT_IN_CHANNEL);
        Broadcast actual = leave.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testLeaveOwnerDeleteChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command join = new JoinCommand(1, "User1", "java");
        join.updateServerModel(model);
        Command leave = new LeaveCommand(0, "User0", "java");
        leave.updateServerModel(model);
        assertTrue(model.getUsersInChannel("java").isEmpty());
        assertFalse(model.getChannels().contains("java"));
    }

    /** Task 5 Tests **/
    @Test
    public void testInviteNoSuchUser() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command invite = new InviteCommand(0, "User0", "java", "User1");
        Broadcast expected = Broadcast.error(invite, ServerResponse.NO_SUCH_USER);
        Broadcast actual = invite.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testInviteNoSuchChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command invite = new InviteCommand(0, "User0", "cis", "User1");
        Broadcast expected = Broadcast.error(invite, ServerResponse.NO_SUCH_CHANNEL);
        Broadcast actual = invite.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testInviteToPublicChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command invite = new InviteCommand(0, "User0", "java", "User1");
        Broadcast expected = Broadcast.error(invite, ServerResponse.INVITE_TO_PUBLIC_CHANNEL);
        Broadcast actual = invite.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testInviteNotFromOwner() {
        model.registerUser(0);
        model.registerUser(1);
        model.registerUser(2);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command join = new JoinCommand(1, "User1", "java");
        join.updateServerModel(model);
        Command invite = new InviteCommand(1, "User1", "java", "User2");
        Broadcast expected = Broadcast.error(invite, ServerResponse.USER_NOT_OWNER);
        Broadcast actual = invite.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testKickNoSuchUser() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command kick = new KickCommand(0, "User0", "java", "User1");
        Broadcast expected = Broadcast.error(kick, ServerResponse.NO_SUCH_USER);
        Broadcast actual = kick.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testKickNoSuchChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command kick = new KickCommand(0, "User0", "cis", "User1");
        Broadcast expected = Broadcast.error(kick, ServerResponse.NO_SUCH_CHANNEL);
        Broadcast actual = kick.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testKickSelfFromEmptyChannel() {
        model.registerUser(0);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command kick = new KickCommand(0, "User0", "java", "User0");
        kick.updateServerModel(model);
        assertTrue(model.getUsersInChannel("java").isEmpty());
        assertFalse(model.getChannels().contains("java"));
    }

    @Test
    public void testKickSelfFromFullChannel() {
        model.registerUser(0);
        model.registerUser(1);
        model.registerUser(2);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command joinOne = new JoinCommand(1, "User1", "java");
        joinOne.updateServerModel(model);
        Command joinTwo = new JoinCommand(2, "User2", "java");
        joinTwo.updateServerModel(model);
        Command kick = new KickCommand(0, "User0", "java", "User0");
        kick.updateServerModel(model);
        assertTrue(model.getUsersInChannel("java").isEmpty());
        assertFalse(model.getChannels().contains("java"));
    }

    @Test
    public void testUserNotInChannel() {
        model.registerUser(0);
        model.registerUser(1);
        Command create = new CreateCommand(0, "User0", "java", true);
        create.updateServerModel(model);
        Command kick = new KickCommand(0, "User0", "java", "User1");
        Broadcast expected = Broadcast.error(kick, ServerResponse.USER_NOT_IN_CHANNEL);
        Broadcast actual = kick.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }

    @Test
    public void testKickUserNotOwner() {
        model.registerUser(0);
        model.registerUser(1);
        model.registerUser(2);
        Command create = new CreateCommand(0, "User0", "java", false);
        create.updateServerModel(model);
        Command joinOne = new JoinCommand(1, "User1", "java");
        joinOne.updateServerModel(model);
        Command joinTwo = new JoinCommand(2, "User2", "java");
        joinTwo.updateServerModel(model);
        Command kick = new KickCommand(1, "User1", "java", "User2");
        Broadcast expected = Broadcast.error(kick, ServerResponse.USER_NOT_OWNER);
        Broadcast actual = kick.updateServerModel(model);
        assertEquals(expected, actual, "Broadcast");
    }
}
