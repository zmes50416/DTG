package tw.edu.ncu.im.Util;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Before;
import org.junit.Test;


public class IndexSearcherTest {
	IndexSearcher searcher;
	@Before
	public void setUp() throws Exception {
			searcher = new IndexSearcher("http://140.115.82.105/searchweb");

	}

	@Test
	public void test() {
		HttpSolrClient service = IndexSearcher.getService(searcher.ip);
		long num =1000;
		try {
			num = this.searcher.searchTermSize("Apple");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(num);
		assertNotNull(service);
	}

}
