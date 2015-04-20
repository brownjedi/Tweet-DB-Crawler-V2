package edu.columbia.gskr.tweetdbcrawler.mybatis.mapper;


import edu.columbia.gskr.tweetdbcrawler.domain.Tweet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

public interface TweetMapper {

    public int getTweetCount();

    public void insertTweet(Tweet tweet);

    public void insertHashTags(@Param("hashTags") List<String> hashTags, @Param("tweetId") long tweetId);

    public void deleteTweetById(long tweetId);

    public void deleteHashTags(long tweetId);

    public long getOldestTweetId();

}
