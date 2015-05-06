package tw.edu.ncu.im.Util;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;


public class IndexSearcherTest {
	IndexSearcher searcher;
	@Before
	public void setUp() throws Exception {
			searcher = new IndexSearcher();

	}

	@Test
	public void testSingleTermSearch() {
		try {
			SolrServer service = IndexSearcher.getService();
			long num = this.searcher.searchTermSize("Apple");
			System.out.println(num);
			
			assertThat(num,not(equalTo((long)0)));
			assertNotNull(service);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	public void testMultiTermsCombinedSearch(){
		try {
			SolrServer service = IndexSearcher.getService();
			String multiTerm[] = {
				"Apple","Google","Samsung","Yahoo"	
			};
			long num = this.searcher.searchMultipleTerm(multiTerm);
			System.out.println(num);
			
			assertThat(num,not(equalTo((long)0)));
			assertNotNull(service);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
