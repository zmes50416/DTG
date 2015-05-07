package tw.edu.ncu.im.Util;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class IndexSearcherTest {
	IndexSearcher searcher;
	@BeforeClass
	public static void init() throws Exception {
		IndexSearcher.SolrHomePath = "F:\\NGD\\NGD\\webpart\\solr";
		IndexSearcher.solrCoreName = "collection1";
		//"C:\\solr-4.9.1\\example\\solr";
		//F:\\NGD\\NGD\\webpart\\solr
	}
	@Before
	public void setUp() throws Exception {
			searcher = new IndexSearcher();
	}

	@Test
	public void testSingleTermSearch() throws SolrServerException {
			long num = this.searcher.searchTermSize("Apple");
			System.out.println(num);
			
			assertThat(num,not(equalTo((long)0)));

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
			e.printStackTrace();
		}

		
		
	}

}
