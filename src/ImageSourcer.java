//import java.util.Vector;
//
//public class ImageSourcer {
//
//	public Vector<String> getImages(String searchText) {
//		// default: dummy return
//		Vector<String> imageURLs = new Vector<String>();
//		imageURLs.addElement("https://news.nationalgeographic.com/content/dam/news/2016/09/08/humpback-whales/01humpbackwhales.jpg");
//		imageURLs.addElement("https://img.purch.com/h/1000/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA4NC8zMjgvb3JpZ2luYWwvbGxtLXdoYWxlLXBlZS5qcGc=");
//		imageURLs.addElement("http://us.whales.org/sites/default/files/styles/large_carousel/public/species/humpback_whale_megaptera_novaeangliae_15_feb-10-11049.jpg?itok=vHlZOW2L");
//		imageURLs.addElement("http://www.whales.org.au/news/images/humpback-s.jpg");
//		imageURLs.addElement("https://www.amazingalbany.com.au/wp-content/uploads/2015/06/Humpback-whale1-1024x683.jpg");
//		imageURLs.addElement("http://www.slate.com/content/dam/slate/articles/video/video/2016/11/whales_swimming_off_new_york_city_tracked_by_scientists_video/whalehellothere.jpg.CROP.promo-xlarge2.jpg");
//		imageURLs.addElement("https://static01.nyt.com/images/2017/01/13/science/13tb-orca01/13tb-orca01-superJumbo.jpg");
//		imageURLs.addElement("https://news.nationalgeographic.com/content/dam/news/2017/03/28/whale-gallery/01-whale-gallery.ngsversion.1490884207593.adapt.1900.1.jpg");
//		imageURLs.addElement("https://i.ytimg.com/vi/o767PuYbEXg/maxresdefault.jpg");
//		imageURLs.addElement("https://img.purch.com/h/1000/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA5Mi80MzIvb3JpZ2luYWwvd2hhbGUtdGFpbC1zaHV0dGVyc3RvY2suanBn");
//
//		imageURLs.addElement("https://news.nationalgeographic.com/content/dam/news/2016/09/08/humpback-whales/01humpbackwhales.jpg");
//		imageURLs.addElement("https://img.purch.com/h/1000/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA4NC8zMjgvb3JpZ2luYWwvbGxtLXdoYWxlLXBlZS5qcGc=");
//		imageURLs.addElement("http://us.whales.org/sites/default/files/styles/large_carousel/public/species/humpback_whale_megaptera_novaeangliae_15_feb-10-11049.jpg?itok=vHlZOW2L");
//		imageURLs.addElement("http://www.whales.org.au/news/images/humpback-s.jpg");
//		imageURLs.addElement("https://www.amazingalbany.com.au/wp-content/uploads/2015/06/Humpback-whale1-1024x683.jpg");
//		imageURLs.addElement("http://www.slate.com/content/dam/slate/articles/video/video/2016/11/whales_swimming_off_new_york_city_tracked_by_scientists_video/whalehellothere.jpg.CROP.promo-xlarge2.jpg");
//		imageURLs.addElement("https://static01.nyt.com/images/2017/01/13/science/13tb-orca01/13tb-orca01-superJumbo.jpg");
//		imageURLs.addElement("https://news.nationalgeographic.com/content/dam/news/2017/03/28/whale-gallery/01-whale-gallery.ngsversion.1490884207593.adapt.1900.1.jpg");
//		imageURLs.addElement("https://i.ytimg.com/vi/o767PuYbEXg/maxresdefault.jpg");
//		imageURLs.addElement("https://img.purch.com/h/1000/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA5Mi80MzIvb3JpZ2luYWwvd2hhbGUtdGFpbC1zaHV0dGVyc3RvY2suanBn");
//		
//		imageURLs.addElement("https://news.nationalgeographic.com/content/dam/news/2016/09/08/humpback-whales/01humpbackwhales.jpg");
//		imageURLs.addElement("https://img.purch.com/h/1000/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA4NC8zMjgvb3JpZ2luYWwvbGxtLXdoYWxlLXBlZS5qcGc=");
//		imageURLs.addElement("http://us.whales.org/sites/default/files/styles/large_carousel/public/species/humpback_whale_megaptera_novaeangliae_15_feb-10-11049.jpg?itok=vHlZOW2L");
//		imageURLs.addElement("http://www.whales.org.au/news/images/humpback-s.jpg");
//		imageURLs.addElement("https://www.amazingalbany.com.au/wp-content/uploads/2015/06/Humpback-whale1-1024x683.jpg");
//		imageURLs.addElement("http://www.slate.com/content/dam/slate/articles/video/video/2016/11/whales_swimming_off_new_york_city_tracked_by_scientists_video/whalehellothere.jpg.CROP.promo-xlarge2.jpg");
//		imageURLs.addElement("https://static01.nyt.com/images/2017/01/13/science/13tb-orca01/13tb-orca01-superJumbo.jpg");
//		imageURLs.addElement("https://news.nationalgeographic.com/content/dam/news/2017/03/28/whale-gallery/01-whale-gallery.ngsversion.1490884207593.adapt.1900.1.jpg");
//		imageURLs.addElement("https://i.ytimg.com/vi/o767PuYbEXg/maxresdefault.jpg");
//		imageURLs.addElement("https://img.purch.com/h/1000/aHR0cDovL3d3dy5saXZlc2NpZW5jZS5jb20vaW1hZ2VzL2kvMDAwLzA5Mi80MzIvb3JpZ2luYWwvd2hhbGUtdGFpbC1zaHV0dGVyc3RvY2suanBn");
//		
//		return imageURLs;
//	}
//
//}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;



public class ImageSourcer {
	
	public Vector<String> getImages(String searchText) {
		/* Arguments: String searchText: the thing that we want to search Google images for - provided by user in client
		 * 
		 * Returns: Vector<String> of URLs. This will either be null (if < 30 images found) or contain 30 image URLs of the
		 * top 30 image results for searchText on Google. */
		
		
		// IMPORTANT NOTE: Google Custom Search API only allows return of 10 items at a time. 
		// SOLUTION: We run the code to fetch 10 items thrice, offsetting the start index by 10 each time. 
		// That is, the contents of the loop is the code to generate 10 image URLs. The loop itself executes 3 times, grabbing the
		// first 10, 11-20, 21-30th images.
		
		// setting up data structures that will be populated by data later
		BufferedReader br; // will be used to read from Google API's input stream
		StringBuilder builder = null; // will build a string out of lines from the input stream
		JSONObject json = null; // holds the JSON object form of the data
		Vector<String> imageURLs = new Vector<String>(); // vector to hold all 30 image urls (to be returned later)
		
		// offset / search loop
		/* loop will perform the following duties:
		 * 	1. grab next 10 images from Google
		 * 	2. parse data, which is returned as a JSON file, to obtain URLs
		 * 	3. append URLs to our vector of URLs */ 
		for(int offset = 0; offset <= 20; offset += 10) // loops 3 times, getting 10 images each
		{
			
			// setting up search parameters
			String qry = "";
			try {
				qry = URLEncoder.encode(searchText, "UTF-8");  // url encoding the raw search string
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String key = "AIzaSyCMLFYK6VlsPwvUKIwB2MvHvWJ_Pf-QAn4"; // this is an API key provided by Google
			String cx  = "004843956391315063069:wnj8zpugysm"; // this is a custom search engine key provided by Google
			String fileType = "png,jpg"; // desired file types
			String searchType = "image"; // type of search to perform
			String startIndex = String.valueOf(offset + 1); // the offset + 1 is the index of the Google results that we want to start returning our 10 results from. Results are 1-indexed.
														// e.g. when offset = 0, start = 1, which means we are returning results 1-10.
			
			// creating connection with Google Custom Search API - see API documentation for parameter details
			HttpURLConnection conn = null;
			try {
				URL url = new URL ("https://www.googleapis.com/customsearch/v1?key=" +key+ "&cx=" +cx+ "&q="+qry+"&fileType="+fileType+"&searchType="+searchType+"&alt=json&start="+startIndex);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// the next two steps will require the string builder
			// string builder will be used to read data from Google's input stream and be converted into a JSON object for parsing
			builder = new StringBuilder();
			
			// retrieving result from Google connection's input stream
			try 
			{
				br = new BufferedReader(new InputStreamReader ( ( conn.getInputStream() ) ) );		
				String line; // temporary variable to store lines from buffered reader as we read from input stream
				
				// read each line one by one and append to our string builder
				while((line = br.readLine()) != null) 
				{
					builder.append(line);
				}
				
				// terminate connection with Google
				conn.disconnect();
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// Google returns result in JSON format
			// converting result to JSON object and then parsing that JSON object to extract data
			String imageUrl = null; // temporary variable to hold current image url as we iterate through data
			try 
			{
				json = new JSONObject(builder.toString());
				for(int i = 0; i < 10; i++)
				{	
					imageUrl = json.getJSONArray("items").getJSONObject(i).getString("link");
					imageURLs.addElement(imageUrl);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			System.out.println("loop end");
		} // end offset and search loop
		
//		for(int i = 0; i < 30; i++)
//		{
//			System.out.println(imageURLs.get(i));
//		}
//		
		// check that 30 image URLs were found
		if(imageURLs.size() == 30)
		{
			return imageURLs;
		}
		else
		{
			return null; // return value of null indicates that < 30 images were found
		}

	} // end getImages(String)

} // end Class ImageSourcer

