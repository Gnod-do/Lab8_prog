package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

public class GroupCountingByCoordinatesCommand extends AbstractCommand{

    private CollectionManager collectionManager;

    public GroupCountingByCoordinatesCommand(CollectionManager collectionManager) {
        super("group_counting_by_coordinates","", "Отображает группы, сгруппированные по координатам");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String commandStringArgument, Object commandObjectArgument, User user) {
        try {
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            collectionManager.groupCountingByCoordinates();
            ResponseOutputer.appendln("сгруппированные учебные группы по координатам");
            return true;
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        }
        return false;
    }
}

