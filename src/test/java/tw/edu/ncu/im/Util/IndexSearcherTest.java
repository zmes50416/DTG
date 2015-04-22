package tw.edu.ncu.im.Util;

import org.junit.Before;
import org.junit.Test;


public class IndexSearcherTest {
	IndexSearcher searcher;
	@Before
	public void setUp() throws Exception {
		try{
			searcher = new IndexSearcher("http://140.115.82.105/searchweb");
			IndexSearcher.getService(searcher.ip);
		}catch(Throwable e){
			System.out.println(e.getMessage());
			
			Throwable[] suppressed = e.getSuppressed();
			for(Throwable a :suppressed){
				System.out.println(a.getMessage());
			}
		}

	}

	@Test
	public void test() {
	}

}
