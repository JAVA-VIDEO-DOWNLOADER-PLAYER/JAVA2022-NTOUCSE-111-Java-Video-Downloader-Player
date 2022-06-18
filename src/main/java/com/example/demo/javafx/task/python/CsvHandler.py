import os.path
import re
import sys
import pandas as pd
import numpy as np


def insertNewVideotoList(title, path):
    InsertTitle = re.sub('[\/:*?"<>|]', '-', title)
    if os.path.exists("test.csv"):
        # 如果 test.csv 存在，則檢查下載之影片(csv)，是否有落網之魚
        sheet = pd.read_csv('test.csv', header=0)
        for index in range(len(sheet)):
            videoInCsv = sheet.loc[index, "video"]
            if videoInCsv == InsertTitle or os.path.abspath(path + title) == True:
                return
        data = np.array([(title, '0', os.path.abspath(path))])
        Newdf = pd.DataFrame(data=data, columns=['video', 'time', 'path'])
        CombiedDf = pd.concat([sheet, Newdf], ignore_index=True)
        # sheet.append(Newdf, ignore_index=True)
        print(CombiedDf)
        CombiedDf.to_csv('test.csv', index=False)


    else:
        # 如果 test.csv 不存在，則新建 csv 並插入新一筆資料
        data = np.array([(title, '0', os.path.abspath(path))])
        df = pd.DataFrame(data=data, columns=['video', 'time', 'path'])
        df.to_csv("test.csv", index=False)

def updateCsv(title, time):
    print(title)
    print(time)
    data = pd.read_csv('test.csv', header=0)
    data.loc[data['video'] == title , 'time' ] = str(time)
    data.to_csv('test.csv',index=False, encoding='utf-8')
    sys.exit(0)
