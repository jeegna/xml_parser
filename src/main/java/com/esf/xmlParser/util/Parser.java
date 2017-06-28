package com.esf.xmlParser.util;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Video;

/**
 * A Parser for fcpxml files
 * 
 * @author Jeegna Patel
 */
public class Parser {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private static final String ASSET = "asset";
	private static final String VIDEO = "video";

	private static final String ID = "id";
	private static final String UID = "uid";
	private static final String NAME = "name";
	private static final String START = "start";
	private static final String LANE = "lane";
	private static final String DURATION = "duration";
	private static final String SOURCE = "src";
	private static final String OFFSET = "offset";
	private static final String REFERENCE = "ref";
	private static final String FORMAT = "format";
	private static final String HAS_AUDIO = "hasAudio";
	private static final String HAS_VIDEO = "hasVideo";
	private static final String AUDIO_SOURCES = "audioSources";
	private static final String AUDIO_CHANNELS = "audioChannels";
	private static final String AUDIO_RATE = "audioRate";

	private static final String AUDIO = "audio";
	private static final String SRC_CH = "srcCh";
	private static final String SRC_ID = "srcID";
	private static final String ROLE = "role";

	private static final String ASSET_CLIP = "asset-clip";
	private static final String AUDIO_ROLE = "audioRole";
	private static final String TC_FORMAT = "tcFormat";

	private Document doc;

	/**
	 * Creates a Parser for the given file.
	 * 
	 * @param file
	 *            The absolute path of the file from which to get an XML
	 *            parsable Document.
	 * 
	 * @throws IOException
	 *             If any IO errors occur.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 */
	public Parser(String file) throws ParserConfigurationException, SAXException, IOException {
		doc = getDocument(file);
	}

	/**
	 * Gets all information of &lt;asset&gt; tags from the document.
	 * 
	 * @return A List of Asset objects with the information from the document.
	 */
	public List<Asset> getAssets() {

		NodeList assets = doc.getElementsByTagName(ASSET);
		List<Asset> list = new ArrayList<Asset>();

		for (int i = 0; i < assets.getLength(); i++) {

			Node node = assets.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Asset asset = new Asset();
				asset.setId(validateString(element.getAttribute(ID)));
				asset.setDuration(validateString(element.getAttribute(DURATION)));

				String hasAudio = element.getAttribute(HAS_AUDIO);
				asset.setHasAudio(hasAudio != null && hasAudio.equals("1"));

				String hasVideo = element.getAttribute(HAS_VIDEO);
				asset.setHasVideo(hasVideo != null && hasVideo.equals("1"));

				asset.setName(validateString(element.getAttribute(NAME)));
				asset.setUid(validateString(element.getAttribute(UID)));
				asset.setSrc(validateString(element.getAttribute(SOURCE)));
				asset.setStart(validateString(element.getAttribute(START)));
				asset.setFormat(validateString(element.getAttribute(FORMAT)));
				asset.setAudioSources(validateNumber(element.getAttribute(AUDIO_SOURCES)));
				asset.setAudioChannels(validateNumber(element.getAttribute(AUDIO_CHANNELS)));
				asset.setAudioRate(validateNumber(element.getAttribute(AUDIO_RATE)));

				list.add(asset);
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;asset-clip&gt; tags from the document.
	 * 
	 * @return A List of AssetClip objects with the information from the
	 *         document.
	 */
	public List<AssetClip> getAssetClips() {

		NodeList assets = doc.getElementsByTagName(ASSET_CLIP);

		List<AssetClip> list = new ArrayList<AssetClip>();

		for (int i = 0; i < assets.getLength(); i++) {

			Node node = assets.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				AssetClip assetClip = new AssetClip();

				assetClip.setRef(validateString(element.getAttribute(REFERENCE)));
				assetClip.setName(validateString(element.getAttribute(NAME)));
				assetClip.setLane(validateNumber(element.getAttribute(LANE)));
				assetClip.setOffset(element.getAttribute(OFFSET));
				assetClip.setDuration(validateString(element.getAttribute(DURATION)));
				assetClip.setStart(element.getAttribute(START));
				assetClip.setRole(validateString(element.getAttribute(AUDIO_ROLE)));
				assetClip.setFormat(validateString(element.getAttribute(FORMAT)));
				assetClip.setTcFormat(validateString(element.getAttribute(TC_FORMAT)));

				list.add(assetClip);
			}
		}

		return list;
	}

	/**
	 * Gets an XML parsable Document from the given XML file name and path.
	 * 
	 * @param file
	 *            The absolute path of the file from which to get an XML
	 *            parsable Document.
	 * 
	 * @return An XML parsable Document from the given file name and path.
	 * 
	 * @throws IOException
	 *             If any IO errors occur.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 */
	private Document getDocument(String file) throws ParserConfigurationException, SAXException, IOException {

		// Get file
		File fcpxml = new File(file);

		// Create document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(fcpxml);

		doc.getDocumentElement().normalize();

		return doc;
	}

	/**
	 * Gets all information of &lt;video&gt; tags from the document.
	 * 
	 * @return A List of Video objects with the information from the document.
	 */
	public List<Video> getVideos() {

		NodeList videos = doc.getElementsByTagName(VIDEO);
		List<Video> list = new ArrayList<Video>();

		for (int i = 0; i < videos.getLength(); i++) {

			Node node = videos.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Video video = new Video();

				video.setName(validateString(element.getAttribute(NAME)));
				video.setLane(validateNumber(element.getAttribute(LANE)));
				video.setOffset(validateString(element.getAttribute(OFFSET)));
				video.setRef(validateString(element.getAttribute(REFERENCE)));
				video.setDuration(validateString(element.getAttribute(DURATION)));
				video.setStart(validateString(element.getAttribute(START)));

				list.add(video);
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;audio&gt; tags from the document.
	 * 
	 * @return A List of Audio objects with the information from the document.
	 */
	public List<Audio> getAudios() {

		NodeList audios = doc.getElementsByTagName(AUDIO);
		List<Audio> list = new ArrayList<Audio>();

		for (int i = 0; i < audios.getLength(); i++) {

			Node node = audios.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Audio audio = new Audio();

				audio.setRef(validateString(element.getAttribute(REFERENCE)));
				audio.setLane(validateNumber(element.getAttribute(LANE)));
				audio.setRole(validateString(element.getAttribute(ROLE)));
				audio.setOffset(validateString(element.getAttribute(OFFSET)));
				audio.setSrcCh(validateString(element.getAttribute(SRC_CH)));
				audio.setSrcID(validateNumber(element.getAttribute(SRC_ID)));

				String duration = validateString(element.getAttribute(DURATION));
				String start = validateString(element.getAttribute(START));
				String offset = validateString(element.getAttribute(OFFSET));
				audio.setDuration(getTime(duration));
				audio.setStart(getTime(start));
				audio.setOffset(getTime(offset));

				list.add(audio);
			}
		}

		return list;
	}

	private String getTime(String s) {
		logger.info("Original time: " + s);
		String time = "0";

		if (s != null && !s.isEmpty()) {
			time = s;
			int division = s.indexOf('/');

			if (division != -1) {
				int length = s.length();

				String num = s.substring(0, division);
				String denom = s.substring(division + 1, length - 1);

				BigInteger numerator = new BigInteger(num);
				BigInteger denominator = new BigInteger(denom);

				time = numerator.divide(denominator).toString();
			}
		}

		if (!time.endsWith("s")) {
			time += "s";
		}
		
		logger.info("New time: " + time);
		return time;
	}

	private int validateNumber(String n) {
		if (n == null || n.isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(n);
		}
	}

	private String validateString(String n) {
		if (n == null || n.isEmpty()) {
			return null;
		} else {
			return n;
		}
	}
}