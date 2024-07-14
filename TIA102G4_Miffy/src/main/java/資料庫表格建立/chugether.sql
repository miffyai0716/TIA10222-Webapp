CREATE DATABASE IF NOT EXISTS chugether;

USE chugether;

-- 1.會員
CREATE TABLE `member`(
   member_id            BIGINT AUTO_INCREMENT  NOT NULL PRIMARY KEY, -- 會員ID
   regis_date    		DATETIME DEFAULT CURRENT_TIMESTAMP, -- 註冊日期
   name            		VARCHAR(50) NOT NULL, -- 會員姓名
   account              VARCHAR(50) NOT NULL UNIQUE, -- 帳號
   password             VARCHAR(50) NOT NULL, -- 密碼
   email                VARCHAR(50) NOT NULL UNIQUE, -- 信箱
   gender               TINYINT NOT NULL, -- 性別
   mobile_no            VARCHAR(11) NOT NULL UNIQUE, -- 手機號碼
   sticker         		LONGBLOB -- 頭貼
);

-- 2.Q&A
CREATE TABLE qa(
    qa_id               BIGINT AUTO_INCREMENT  NOT NULL PRIMARY KEY, -- 問題ID
    content            	VARCHAR(500) NOT NULL, -- 問題內容
    answer          	VARCHAR(500) NOT NULL, -- 回答
    author_id           BIGINT NOT NULL, -- 建檔者ID
    create_time        	DATETIME DEFAULT CURRENT_TIMESTAMP, -- 建檔時間
    last_updated        VARCHAR(50) NOT NULL, -- 最後更新者
    last_updated_time   DATETIME DEFAULT CURRENT_TIMESTAMP -- 最後更新時間        
);

-- 3.餐廳類別
CREATE TABLE restaurant_type(
	type_id     		BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 餐廳類別ID
	type_name 			VARCHAR(10) NOT NULL UNIQUE -- 餐廳類別名稱
);

-- 4.餐廳
CREATE TABLE restaurant(
	rest_id 			BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 餐廳ID
	type_id 			BIGINT NOT NULL, -- 餐廳類別ID
	rest_name 			VARCHAR(10) NOT NULL UNIQUE, -- 餐廳名稱
	description 		VARCHAR(500) NOT NULL, -- 餐廳描述
	location 			VARCHAR(50) NOT NULL, -- 餐廳位置
	phone 				VARCHAR(10)NOT NULL UNIQUE, -- 餐廳電話
	regist_time	 		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 註冊時間
	email 				VARCHAR(100) NOT NULL UNIQUE, -- 信箱
	open_day 			VARCHAR(7) NOT NULL, -- 營業時間：0.公休、1.營業
	open_time1 			TIME NOT NULL, -- 營業時段1
	close_time1 		TIME NOT NULL, -- 打烊時間1
	open_time2 			TIME NULL, -- 營業時段2
	close_time2 		TIME NULL, -- 打烊時間2
	password 			VARCHAR(50) NOT NULL, -- 餐廳密碼
	rating_pnum 		INT, -- 評分總人數
	rating_star 		INT, -- 評分總星星數
	CONSTRAINT restaurant_fk FOREIGN KEY(type_id) REFERENCES restaurant_type(type_id)
);

-- 5.訂單資料
CREATE TABLE `order`(
	order_id 			BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 訂單ID
	rest_id 			BIGINT NOT NULL, -- 餐廳ID
	member_id 			BIGINT NOT NULL, -- 會員ID
	event_id			BIGINT NOT NULL, -- 活動ID
	name 				VARCHAR(50), -- 主揪者姓名
	phone 				VARCHAR(10), -- 主揪者電話
	order_date 			DATETIME NOT NULL, -- 訂單日期
	order_status 		INT NOT NULL, -- 訂位狀態：1. 預約 2.被取消 3.已完成
	reser_date 			DATE NOT NULL, -- 訂單日期
	reser_time 			TIME NOT NULL, -- 訂單時間
	reser_people_numbe 	INT NOT NULL, -- 訂位人數
	reser_note 			VARCHAR(50) NOT NULL, -- 訂位備註 
	CONSTRAINT order_fk1 FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id),
	CONSTRAINT order_fk2 FOREIGN KEY(member_id) REFERENCES `MEMBER`(member_id)
);

-- 6.餐廳消息
CREATE TABLE restaurant_news(
	news_id 			BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 消息ID
	type 				INT NOT NULL, -- 消息類別ID: 1.公告、2.廣告
	rest_id 			BIGINT NOT NULL, -- 餐廳ID
	start_time 			DATE NOT NULL, -- 消息開始時間
	end_time 			DATE NOT NULL, -- 消息結束時間
	heading 			VARCHAR(50) NOT NULL, -- 消息主旨
	content 			VARCHAR(500) NOT NULL, -- 消息內容
	image 				LONGBLOB NULL, -- 消息圖片
	CONSTRAINT restaurant_news_fk FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id)
);

-- 7.黑名單
CREATE TABLE black_list(
	black_id 			BIGINT NOT NULL PRIMARY KEY, -- 黑名單ID
	member_id 			BIGINT NOT NULL, -- 會員ID
	rest_id 			BIGINT NOT NULL, -- 餐廳ID
	add_blist_time	 	DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 加入黑名單時間
	CONSTRAINT black_list_fk1 FOREIGN KEY(member_id) REFERENCES `member`(member_id),
	CONSTRAINT black_list_fk2 FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id)
);

-- 8.餐廳菜單
CREATE TABLE menu_image(
	menu_image_id 		BIGINT NOT NULL PRIMARY KEY, -- 菜單圖片ID
	rest_id 			BIGINT NOT NULL, -- 餐廳ID
	image 				LONGBLOB NOT NULL, -- 菜單圖片
	image_name 			VARCHAR(50) NOT NULL, -- 菜單圖片名稱
	CONSTRAINT menu_image_fk FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id)
);

-- 9.後台人員
CREATE TABLE admin (
	admin_id 			BIGINT  AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 後台人員ID
	name 				VARCHAR(50) NOT NULL, -- 名稱
	account 			VARCHAR(50) NOT NULL UNIQUE, -- 帳號
	password		 	VARCHAR(50) NOT NULL DEFAULT 'chugether', -- 密碼
	permission 			INT NOT NULL, -- 權限 1.管理員 2.一般
	regis_date 			DATETIME DEFAULT CURRENT_TIMESTAMP -- 註冊日期
);

-- 10.公告
CREATE TABLE announcement(
	anno_id 			BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 公告ID
	start_time 			DATE, -- 起始日期
	end_time 			DATE, -- 結束日期
	heading 			VARCHAR(50) NOT NULL, -- 主旨
	content 			VARCHAR(500) NOT NULL, -- 內容
	type 				INT NOT NULL, -- 類型 1.食安新聞 2.系統公告
	image 				LONGBLOB -- 圖片
);

-- 11.客服
CREATE TABLE customer_service (
	cs_id 				BIGINT  AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 客服ID
	rest_id 			BIGINT, -- 餐廳ID
	member_id 			BIGINT, -- 會員ID
	admin_id 			BIGINT  NOT NULL, -- 後台人員ID
	email 				VARCHAR(50) UNIQUE, -- 信箱
	feedback_type 		VARCHAR(50) NOT NULL, -- 意見反應類別
	feedback_content 	VARCHAR(500) NOT NULL, -- 意見反應內容
	feedback_time 		DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 意見反應時間
	reply_heading 		VARCHAR(50) NOT NULL, -- 回覆主旨
	reply_content 		VARCHAR(500) NOT NULL, -- 回覆內容
	reply_time 			DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 回覆時間
	reply_status		TINYINT NOT NULL, -- 回覆狀態
	CONSTRAINT cs_fk1 FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id),
	CONSTRAINT cs_fk2 FOREIGN KEY(member_id) REFERENCES `member`(member_id),
	CONSTRAINT cs_fk3 FOREIGN KEY(admin_id) REFERENCES admin(admin_id)
);

-- 12.餐後評價
CREATE TABLE comment(
    comment_id          BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 餐後評價ID
    rest_id          	BIGINT NOT NULL, -- 餐廳ID
    member_id           BIGINT NOT NULL, -- 會員ID
    rating              INT NOT NULL, -- 評價星等
    content            	VARCHAR(50) NOT NULL, -- 評價內文
    CONSTRAINT comment_fk FOREIGN KEY(member_id) REFERENCES `member`(member_id),
    CONSTRAINT comment_fk2 FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id)
);

-- 13.收藏店家
CREATE TABLE favorite_restaurant(
    fav_rest_id 		BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 收藏店家ID
    member_id           BIGINT NOT NULL, -- 會員ID
    rest_id          	BIGINT NOT NULL, -- 餐廳ID
    add_time            DATETIME DEFAULT CURRENT_TIMESTAMP, -- 收藏日期
    CONSTRAINT favorite_restaurant_fk FOREIGN KEY(member_id) REFERENCES `member`(member_id),
    CONSTRAINT favorite_restaurant_fk2 FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id)
);

-- 14.活動
CREATE TABLE event(
    event_id            BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 活動ID
    member_id           BIGINT NOT NULL, -- 主揪者ID
    name          		VARCHAR(50)  NOT NULL, -- 活動名稱
    date                DATE NOT NULL, -- 預定日期
    time                TIME NOT NULL, -- 預定時間
    info          		VARCHAR(50) NOT NULL, -- 成團訊息
    seat                INT NOT NULL, -- 參與人數
    max_seat            INT NOT NULL, -- 人數上限
    qr_code             VARCHAR(50) NOT NULL, -- 邀請碼
    CONSTRAINT event_fk FOREIGN KEY(member_id) REFERENCES `member`(member_id)
);


-- 15.活動等待室
CREATE TABLE room(
    room_id             BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 等待室ID
    member_id           BIGINT NOT NULL, -- 參與者ID
    event_id            BIGINT NOT NULL, -- 活動ID
    est_time     		DATETIME DEFAULT CURRENT_TIMESTAMP, -- 等待室建立時間
    join_time           DATETIME DEFAULT CURRENT_TIMESTAMP, -- 會員加入等待室時間
    status              TINYINT NOT NULL, -- 審核狀態
    CONSTRAINT room_fk FOREIGN KEY(member_id) REFERENCES `member`(member_id),
    CONSTRAINT room_fk2 FOREIGN KEY(event_id) REFERENCES event(event_id)
);

-- 16.投票選擇
CREATE TABLE vote(
    vote_id         	BIGINT AUTO_INCREMENT  NOT NULL PRIMARY KEY, -- 投票ID
    rest_id          	BIGINT NOT NULL, -- 餐廳ID
    event_id            BIGINT NOT NULL, -- 活動ID
    count               INT, -- 票數
    limit_time          TIME, -- 限制時間
    CONSTRAINT choice_fk FOREIGN KEY(rest_id) REFERENCES restaurant(rest_id),
    CONSTRAINT choice_fk2 FOREIGN KEY(event_id) REFERENCES event(event_id)
);








