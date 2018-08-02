package modelsTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import models.Berserker;
import models.Pair;

class BerserkerTest {

	Berserker berserker = new Berserker(0,0,'N',1,1,1);
	ArrayList<Pair<Integer,Integer>> listAttacks = new ArrayList<Pair<Integer,Integer>>();
	
	@Test
	void test_getAttacks_TopLeftCornerPosition() {
		berserker.setPosX(0);
		berserker.setPosY(0);
		listAttacks = berserker.getAttacks();
		assertEquals(3, listAttacks.size());
		assertEquals("Pair [left=1, right=0]", listAttacks.get(0).toString());
		assertEquals("Pair [left=1, right=1]", listAttacks.get(1).toString());
		assertEquals("Pair [left=0, right=1]", listAttacks.get(2).toString());
	}
	
	@Test
	void test_getAttacks_TopRightCornerPosition() {
		berserker.setPosX(3);
		berserker.setPosY(0);
		listAttacks = berserker.getAttacks();
		assertEquals(3, listAttacks.size());
		assertEquals("Pair [left=3, right=1]", listAttacks.get(0).toString());
		assertEquals("Pair [left=2, right=1]", listAttacks.get(1).toString());
		assertEquals("Pair [left=2, right=0]", listAttacks.get(2).toString());
	}
	
	@Test
	void test_getAttacks_BottomLeftCornerPosition() {
		berserker.setPosX(0);
		berserker.setPosY(3);
		listAttacks = berserker.getAttacks();
		assertEquals(3, listAttacks.size());
		assertEquals("Pair [left=0, right=2]", listAttacks.get(0).toString());
		assertEquals("Pair [left=1, right=2]", listAttacks.get(1).toString());
		assertEquals("Pair [left=1, right=3]", listAttacks.get(2).toString());
	}
	
	@Test
	void test_getAttacks_BottomRightCornerPosition() {
		berserker.setPosX(3);
		berserker.setPosY(3);
		listAttacks = berserker.getAttacks();
		assertEquals(3, listAttacks.size());
		assertEquals("Pair [left=3, right=2]", listAttacks.get(0).toString());
		assertEquals("Pair [left=2, right=3]", listAttacks.get(1).toString());
		assertEquals("Pair [left=2, right=2]", listAttacks.get(2).toString());
	}

}
