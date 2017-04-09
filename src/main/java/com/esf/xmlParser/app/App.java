package com.esf.xmlParser.app;

import java.util.List;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Video;
import com.esf.xmlParser.util.AssetComparator;
import com.esf.xmlParser.util.ListUtilities;
import com.esf.xmlParser.util.Parser;

/**
 * A application for parsing fcpxml files
 * 
 * @author Jeegna Patel
 */
public class App {

	public static void main(String[] args) {
		try {
			Parser parser = new Parser("/home/jeegna/Downloads/file.fcpxml");

			getAudios(parser);
			//getVideos(parser);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getVideos(Parser parser) {
		// Get and sort assets by id
		List<Asset> assets = parser.getAssets();
		assets.sort(new AssetComparator());

		List<Video> videos = parser.getVideos();
		List<AssetClip> assetClips = parser.getAssetClips();

		int counter = 0;
		for (Video video : videos) {
			Asset key = new Asset();
			key.setId(video.getRef());

			int index = ListUtilities.binarySearch(assets, key);

			Asset asset = assets.get(index);
			System.out.println(++counter + ":");
			System.out.println("Name: " + asset.getName());
			System.out.println("Lane: " + video.getLane());
			System.out.println("Clip start: " + video.getStart());
			System.out.println("Clip duration: " + video.getDuration());
			System.out.println("Offset in clip: " + video.getOffset());
			System.out.println("Has audio? " + asset.hasAudio());
			System.out.println("Has video? " + asset.hasVideo());
			System.out.println("Source file: " + asset.getSrc());
			System.out.println();
		}

		for (AssetClip assetClip : assetClips) {
			Asset key = new Asset();
			key.setId(assetClip.getRef());

			int index = ListUtilities.binarySearch(assets, key);

			Asset asset = assets.get(index);

			if (asset.hasVideo()) {
				System.out.println(++counter + ":");
				System.out.println("Name: " + asset.getName());
				System.out.println("Lane: " + assetClip.getLane());
				System.out.println("Clip start: " + assetClip.getStart());
				System.out.println("Clip duration: " + assetClip.getDuration());
				System.out.println("Offset in clip: " + assetClip.getOffset());
				System.out.println("Has audio? " + asset.hasAudio());
				System.out.println("Has video? " + asset.hasVideo());
				System.out.println("Source file: " + asset.getSrc());
				System.out.println("Role: " + assetClip.getRole());
				System.out.println();
			}
		}
	}

	public static void getAudios(Parser parser) {
		// Get and sort assets by id
		List<Asset> assets = parser.getAssets();
		assets.sort(new AssetComparator());

		List<Audio> audios = parser.getAudios();
		List<AssetClip> assetClips = parser.getAssetClips();

		int counter = 0;
		for (Audio audio : audios) {
			Asset key = new Asset();
			key.setId(audio.getRef());

			int index = ListUtilities.binarySearch(assets, key);

			Asset asset = assets.get(index);
			System.out.println(++counter + ":");
			System.out.println("Name: " + asset.getName());
			System.out.println("Lane: " + asset.getId());
			System.out.println("Clip start: " + asset.getStart());
			System.out.println("Clip duration: " + asset.getDuration());
			System.out.println("Offset in clip: " + audio.getOffset());
			System.out.println("Has audio? " + asset.hasAudio());
			System.out.println("Has video? " + asset.hasVideo());
			System.out.println("Source file: " + asset.getSrc());
			System.out.println("Role: " + audio.getRole());
			System.out.println();
		}

		for (AssetClip assetClip : assetClips) {
			Asset key = new Asset();
			key.setId(assetClip.getRef());

			int index = ListUtilities.binarySearch(assets, key);

			Asset asset = assets.get(index);

			if (asset.hasAudio()) {
				System.out.println(++counter + ":");
				System.out.println("Name: " + asset.getName());
				System.out.println("Lane: " + assetClip.getLane());
				System.out.println("Clip start: " + assetClip.getStart());
				System.out.println("Clip duration: " + assetClip.getDuration());
				System.out.println("Offset in clip: " + assetClip.getOffset());
				System.out.println("Has audio? " + asset.hasAudio());
				System.out.println("Has video? " + asset.hasVideo());
				System.out.println("Source file: " + asset.getSrc());
				System.out.println("Role: " + assetClip.getRole());
				System.out.println();
			}
		}
	}
}
