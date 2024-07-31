#!/bin/sh

# 確保目標目錄存在
mkdir -p /usr/share/nginx/html

# 複製 dist 資料夾的内容到 nginx 默認的 html 目錄
cp -r /tmp/dist1/ /usr/share/nginx/html/
cp -r /tmp/dist2/ /usr/share/nginx/html/