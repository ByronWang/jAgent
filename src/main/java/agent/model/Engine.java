package agent.model;

import java.util.List;

public interface Engine {
	void clear();

	// void train(string sample);
	void trainNew(String sample);

	Cell find(String sample);

	List<Cell> getCells();

	Cell add(Cell cell);

	int getLength();

	Cell getCell(int index);

	void setItem(int index, Cell value);
}
