import os.path
import pandas as pd
import numpy as np


def checkIfVideoExist(title=None):
    sheet = pd.read_csv('test.csv')
    df = pd.DataFrame(sheet, columns=['video', 'time', 'path'])
    arrayindex = []
    if title is not None:
        for i in range(len(df)):
            video = df.loc[i, "path"] + df.loc[i, "video"] + ".mp4"
            print(video)
            if not os.path.exists(video):
                arrayindex.append(i)
        print(arrayindex)
        df.drop(index=arrayindex, inplace=True)
        df.to_csv("test.csv", index=False)


def checkIfExist():
    return os.path.exists("test.csv")


def insertNewVideotoList(title, output_path):
    if not checkIfExist():
        # TODO 檢查 videoList 是否建立，若 False 則新建一個
        df = pd.DataFrame(columns=['video', 'time', 'path'])
        df.to_csv("test.csv", index=False)
    checkIfVideoExist()
    # TODO 讀取 CSV
    sheet = pd.read_csv('test.csv')
    insertVideotoList(title, output_path)


def insertVideotoList(title, output_path):
    sheet = pd.read_csv('test.csv')
    df = pd.DataFrame(sheet, columns=['video', 'time', 'path'])
    for i in range(len(df)):
        video = df.loc[i, "video"]
        print(video)
        if video == title:
            return
    # TODO 建立輸入資料
    data = np.array([(title, os.path.abspath(output_path), 0)])
    # TODO 輸入資料至 CSV
    df = pd.DataFrame.from_records(data, columns=['video', 'time', 'path'])
    df.to_csv("test.csv", index=False)
