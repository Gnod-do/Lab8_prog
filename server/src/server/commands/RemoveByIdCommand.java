package server.commands;

import common.data.StudyGroup;
//import exceptions.*;
import common.exceptions.*;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

public class RemoveByIdCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_by_id", "<ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */

    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            long id = Long.parseLong(stringArgument);
            StudyGroup groupToRemove = collectionManager.getById(id);
            if (groupToRemove == null) throw new GroupNotFoundException();
            if (!groupToRemove.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkGroupUserId(groupToRemove.getId(), user)) throw new ManualDatabaseEditException();
            databaseCollectionManager.deleteGroupById(id);
            collectionManager.removeFromCollection(groupToRemove);
            collectionManager.loadCollection();
            ResponseOutputer.appendln("GroupWasDeleted");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("CollectionIsEmptyException");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("IdMustBeNumberException");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("IdOfGroupException");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("DatabaseHandlingException");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("NoughRightsException");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("ManualDatabaseException");
        }
        return false;
    }
}

