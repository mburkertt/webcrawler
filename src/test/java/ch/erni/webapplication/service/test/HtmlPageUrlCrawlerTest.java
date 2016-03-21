package ch.erni.webapplication.service.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ch.erni.webapplication.service.HtmlDocumentParser;
import ch.erni.webapplication.service.HtmlPageUrlCrawler;

public class HtmlPageUrlCrawlerTest {

	@Test
	public void testNullSafeCaseForGetListWithUrlsFromWebPage() {
		HtmlDocumentParser htmlDocumentParser = new HtmlDocumentParser(null);
		HtmlPageUrlCrawler htmlPageUrlCrawler = new HtmlPageUrlCrawler();
		assertNotNull(htmlPageUrlCrawler.getListWithUrlsFromWebPage(htmlDocumentParser.documentBuilder("https://www.google.de/")));
	}

}
