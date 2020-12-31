import CityComponents.City;
import SpecificationVars.OutputsParser;
import org.junit.Test;

public class MoveBusTest {

    @Test
    public void moveOnLineANoStops() throws Exception {
        City city = new City();
        Board board = new Board(city);
        MockRunner runner = new MockRunner(board, city, new MockOutputsParserNoStops(city));
        runner.run();
    }

    @Test
    public void moveOnLineAAlwaysStop() throws Exception {
        City city = new City();
        Board board = new Board(city);
        MockRunner runner = new MockRunner(board, city, new MockOutputsParserAlwaysStop(city));
        runner.run();
    }

    @Test
    public void moveOnLineGoToGas() throws Exception {
        City city = new City();
        Board board = new Board(city);
        MockRunner runner = new MockRunner(board, city, new MockOutpusParserGoToGasStation(city));
        runner.run();


    }

    @Test
    public void real() throws Exception {
        City city = new City();
        Board board = new Board(city);
        Runner runner = new Runner(board, city);
        runner.run();


    }
}
