CREATE TABLE `Tweets` (
    `id` bigint(20) NOT NULL,
    `userScreenName` varchar(512) DEFAULT NULL,
    `userLocation` varchar(1024) DEFAULT NULL,
    `profileImageURL` varchar(2048) DEFAULT NULL,
    `statusText` mediumtext,
    `latitude` double DEFAULT NULL,
    `longitude` double DEFAULT NULL,
    `createdDate` datetime DEFAULT NULL,
    `updatedDate` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `HashTags` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `tweetId` bigint(20) NOT NULL,
    `hashTag` varchar(512) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `tweetId` (`tweetId`),
    CONSTRAINT `hashtags_ibfk_1` FOREIGN KEY (`tweetId`) REFERENCES `Tweets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Sentiments` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `tweetId` bigint(20) NOT NULL,
    `language` varchar(512) NOT NULL,
    `isMixed` tinyint(1) NOT NULL,
    `score` varchar(64) NOT NULL,
    `type` varchar(64) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `tweetId` (`tweetId`),
    CONSTRAINT `sentiments_ibfk_1` FOREIGN KEY (`tweetId`) REFERENCES `Tweets` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
