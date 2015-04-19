package tw.edu.ncu.im.Preprocess;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;

import qtag.Tagger;

public class QtagTest {
	Tagger g;
	@Before
	public void setUp() throws Exception {
		g = new Tagger("qtag-eng");
	}
	//proved Qtag is work related to the word order
	@Test
	public void test() {
		ArrayList<String> stringList = new ArrayList<String>();

		StringTokenizer s = new StringTokenizer("Steve jumps on the table");
		String[] sentence = new String[s.countTokens()];
		int i= 0;
		while(s.hasMoreTokens()){
			String token = s.nextToken();
			sentence[i++] = token;
			stringList.add(token);
			
		}
		String[] tag = g.tag(stringList);
		System.out.println(tag);
		System.out.println(g.tag(sentence).toString());
	}

}
