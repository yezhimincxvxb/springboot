-- 使用关系型数据库存储token
-- 除了数据源，那么jdbc确定得有库表，所以OAuth2默认给出了表结构api
Drop table  if exists oauth_access_token;
create table oauth_access_token (
    create_time timestamp default now(),
    token_id VARCHAR(255),
    token BLOB,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication BLOB,
    refresh_token VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Drop table  if exists oauth_refresh_token;
create table oauth_refresh_token (
    create_time timestamp default now(),
    token_id VARCHAR(255),
    token BLOB,
    authentication BLOB
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 从数据库读取参数配置客户端
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
    `client_id` varchar(48) NOT NULL,
    `resource_ids` varchar(256) DEFAULT NULL,
    `client_secret` varchar(256) DEFAULT NULL,
    `scope` varchar(256) DEFAULT NULL,
    `authorized_grant_types` varchar(256) DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) DEFAULT NULL,
    `authorities` varchar(256) DEFAULT NULL,
    `access_token_validity` int(11) DEFAULT NULL,
    `refresh_token_validity` int(11) DEFAULT NULL,
    `additional_information` varchar(4096) DEFAULT NULL,
    `autoapprove` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;