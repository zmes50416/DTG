package tw.edu.ncu.im.Util;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.apache.lucene.search.IndexSearcher;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class IndexSearcherTest {
	EmbeddedIndexSearcher searcher;
	@BeforeClass
	public static void init() throws Exception {
		Properties setting = new Properties();
		setting.loadFromXML(new FileInputStream("setting.xml"));
		EmbeddedIndexSearcher.SolrHomePath = setting.getProperty("SolrHomePath");
		EmbeddedIndexSearcher.solrCoreName = setting.getProperty("SolrCoreName");
		HttpIndexSearcher.url = setting.getProperty("SolrURL");
	}
	@Before
	public void setUp() throws Exception {
			searcher = new EmbeddedIndexSearcher();
	}
	@Test
	public void testTheSame() throws SolrServerException{//Check whether Embedd or http will affect the result or not
		double a = searcher.searchTermSize("Google");
		double b = searcher.searchMultipleTerm(new String[]{"Google","Yahoo"});
		double aa = new HttpIndexSearcher().searchTermSize("Google");
		double bb = new HttpIndexSearcher().searchMultipleTerm(new String[]{"Google","Yahoo"});
		assertEquals("Search result should be the same no matter from Http or Embedded",a,aa,0);
		assertEquals("Search result should be the same no matter from Http or Embedded",b,bb,0);
	}
	@Test
	public void testSingleTermSearch() throws SolrServerException {
			long num = this.searcher.searchTermSize("Apple");
			
			assertThat(num,not(equalTo((long)0)));

	}
	@Test
	public void testMultiTermsCombinedSearch(){
		try {
			String multiTerm[] = {
				"Google","Yahoo"	
			};
			long num = this.searcher.searchMultipleTerm(multiTerm);
			
			assertThat(num,not(equalTo((long)0)));
			assertTrue("Should always smaller or equal the single result",num<=this.searcher.searchTermSize("Apple"));
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
	}

}
