package com.craftlauncher.launcher.logic.download.loader;

import com.craftlauncher.launcher.exception.CraftenDownloadException;

public interface Downloader {
	/**
	 * Downloads the specified file.
	 *
	 * @throws CraftenDownloadException if downloading the file fails
	 */
	void download() throws CraftenDownloadException;
}
