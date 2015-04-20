package edu.columbia.gskr.tweetdbcrawler.mybatis.service;

import edu.columbia.gskr.tweetdbcrawler.domain.Tweet;
import edu.columbia.gskr.tweetdbcrawler.mybatis.mapper.TweetMapper;
import edu.columbia.gskr.tweetdbcrawler.mybatis.util.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

@SuppressWarnings("unused")
public class TweetService {

    public void insertTweet(Tweet tweet) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.insertTweet(tweet);
            if (tweet.getHashTags() != null && tweet.getHashTags().size() > 0) {
                tweetMapper.insertHashTags(tweet.getHashTags(), tweet.getId());
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public int getTweetCount() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        int count = 0;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            count = tweetMapper.getTweetCount();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return count;
    }

    public long getOldestTweetId() {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession(TransactionIsolationLevel.READ_UNCOMMITTED);
        long tweetId;
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetId = tweetMapper.getOldestTweetId();
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        return tweetId;
    }

    public void deleteTweetById(long tweetId) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.deleteHashTags(tweetId);
            tweetMapper.deleteTweetById(tweetId);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
