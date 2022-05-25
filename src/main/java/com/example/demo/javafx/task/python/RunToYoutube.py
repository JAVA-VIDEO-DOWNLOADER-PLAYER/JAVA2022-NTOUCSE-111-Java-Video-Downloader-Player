# 引入所需封包
import re

from pytube import YouTube
from pytube import Playlist
import sys


def video_download(video):  # TODO 下載單支影片
    try:
        title = re.sub('[\/:*?"<>|]', '-', video.title)
        video.streams.get_highest_resolution().download(output_path=output_path, filename=title + ".mp4")
        try:
            try:
                # TODO 嘗試下載 中文字幕
                video.captions.get_by_language_code('zh').download(output_path=output_path, title=title)
            except:
                # TODO 若無法下載 中文字幕 -> 改嘗試下載 自動字幕
                video.captions.get_by_language_code('a.en').download(output_path=output_path, title=title)
        except Exception:  # TODO 若無法下載字幕
            print("無字幕")
        finally:
            return True
    except Exception as e:
        print(e)
        return False


# TODO 取出該 command 參數並設置
Video_URL = sys.argv[1] if len(sys.argv) >= 3 else ''
output_path = sys.argv[3] if len(sys.argv) == 4 else ''
# TODO 下載影片
if 'playlist?list=' in Video_URL:  # TODO 下載影片清單
    amount_failed = 0
    try:
        playlist = Playlist(Video_URL)

        for video_url in playlist.video_urls:
            try:
                video = YouTube(video_url)
                if not video_download(video):
                    amount_failed += 1  # TODO 下載失敗跳至下一部繼續
            except Exception:
                amount_failed += 1  # TODO 無法下載跳至下一部繼續

        if amount_failed > 0:  # TODO 表示只有部分影片成功下載
            sys.exit(1)
        sys.exit(0)  # TODO 表示全部成功下載
    except Exception:  # TODO 表示該連結不存在或是出現異常
        sys.exit(3)  # TODO 全部下載失敗
else:  # TODO 下載單支影片
    try:
        video = YouTube(Video_URL)
        if not video_download(video):
            print(123)
            sys.exit(3)
        sys.exit(0)  # TODO 表示成功下載
    except Exception:
        sys.exit(3)  # TODO 下載失敗
