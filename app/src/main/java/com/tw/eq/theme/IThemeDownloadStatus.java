package com.tw.eq.theme;

public interface IThemeDownloadStatus {
    public static class a {
        public String a;
        public String b;
        public int c;
        public boolean d;
        public String e;

        public final String toString() {
            return "DownloadInfo{themeId='" + this.a + "', themeName='" + this.b + "', progress=" + this.c + ", result=" + this.d + ", address='" + this.e + "'}";
        }
    }

    void onDownloadFinish(a aVar);
    void onDownloadStart(a aVar);
    void onDownloading(a aVar);
}
