// IDownload.aidl
package andorid.taow.selfview;

// Declare any non-default types here with import statements
import andorid.taow.selfview.IDownloadCallback;
interface IDownload {
    int getIpState();
	void setIpUpgradeCallback(IDownloadCallback listener);
	void operateIpService(int opCode);
}
