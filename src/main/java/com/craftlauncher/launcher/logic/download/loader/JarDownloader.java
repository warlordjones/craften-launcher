package com.craftlauncher.launcher.logic.download.loader;

import java.io.File;

import com.craftlauncher.launcher.exception.CraftenDownloadException;
import com.craftlauncher.launcher.logic.download.DownloadHelper;
import com.craftlauncher.launcher.logic.download.DownloadUrls;
import com.craftlauncher.launcher.logic.minecraft.MinecraftPathImpl;
import com.craftlauncher.launcher.logic.version.MinecraftVersion;

public class JarDownloader implements Downloader {

	private MinecraftVersion mVersion;
	private MinecraftPathImpl mMinecraftPath;

	/**
	 * @param version
	 * @param mcPath
	 */
	public JarDownloader(MinecraftVersion version, MinecraftPathImpl mcPath) {
		this.mVersion = version;
		this.mMinecraftPath = mcPath;
	}

	@Override
	public void download() throws CraftenDownloadException {
		String version = this.mVersion.getVersion();
		String path = mMinecraftPath.getMinecraftJarPath();

		new File(path).mkdirs();

		DownloadHelper.downloadFileToDiskWithoutCheck(DownloadUrls.URL_VERSION + version + "/" + version + ".jar", path,
				version + ".jar");
	}

}
