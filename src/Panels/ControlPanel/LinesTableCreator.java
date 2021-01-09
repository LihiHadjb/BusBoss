package Panels.ControlPanel;

import CityComponents.City;
import Lines.Line;

public class LinesTableCreator extends TableCreator {
    final int LINE_NAME_INDEX = 0;
    final int NEED_ANOTHER_INDEX = 1;
    final int UNSTOPPED_COUNT_INDEX = 2;

    final String LINE_NAME_HEADER = "Line";
    final String NEED_ANOTHER_HEADER = "Backup Request";
    final String UNSTOPPED_COUNT_HEADER = "Missed Pickups";


    @Override
    public Object[][] createValues(City city) {
        int i = 0;
        String[][] result = new String[city.getNumLines()][3];
        for(Line line : city.getLines()){
            String[] row = new String[3];
            row[LINE_NAME_INDEX] =line.getLineName().name();
            row[NEED_ANOTHER_INDEX] = Boolean.toString(line.isWaiting());
            row[UNSTOPPED_COUNT_INDEX] = Integer.toString(line.getNumUnstopped());
            result[i] = row;
            i++;
        }
        return result;
    }

    @Override
    public Object[] createHeaders() {
        Object[] result = new String[3];
        result[LINE_NAME_INDEX] = LINE_NAME_HEADER;
        result[NEED_ANOTHER_INDEX] = NEED_ANOTHER_HEADER;
        result[UNSTOPPED_COUNT_INDEX] = UNSTOPPED_COUNT_HEADER;
        return result;

    }
}
