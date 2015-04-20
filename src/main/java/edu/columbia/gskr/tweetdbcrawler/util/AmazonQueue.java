package edu.columbia.gskr.tweetdbcrawler.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by saikarthikreddyginni on 4/16/15.
 */
public class AmazonQueue {

    private static AmazonSQS sqs = null;
    public static String QUEUE_URL = null;
    public static final Logger LOGGER = Logger.getLogger(AmazonQueue.class.getCanonicalName());

    private AmazonQueue() {

    }

    public static AmazonSQS getQueue() {

        if (sqs == null) {

            Properties queueProperties = new Properties();
            InputStream input = AmazonQueue.class.getClassLoader().getResourceAsStream("queue.properties");
            try {
                queueProperties.load(input);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "IOException Occurred when getting the twitter.properties", ex);
                System.exit(-1);
            }

            AWSCredentials credentials = null;
            try {
                credentials = new ProfileCredentialsProvider("default").getCredentials();
            } catch (Exception ex) {
                throw new AmazonClientException("Cannot load the credentials from the credential profiles file. " +
                        "Please make sure that your credentials file is at the correct " +
                        "location (/Users/<user>/.aws/credentials), and is in valid format.", ex);
            }

            sqs = new AmazonSQSClient(credentials);
            Region usEast1 = Region.getRegion(Regions.US_EAST_1);
            sqs.setRegion(usEast1);

            System.out.println("===========================================");
            System.out.println("Getting Started with Amazon SQS");
            System.out.println("===========================================\n");

            try {
                // Create a queue
                System.out.println("Creating a new SQS queue called TweetQueue.\n");
                CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueProperties.getProperty("queue.name"));
                QUEUE_URL = sqs.createQueue(createQueueRequest).getQueueUrl();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error Occurred while creating an SQS Queue", ex);
            }
        }

        return sqs;
    }

    public static void sendMessage(String message) {
        try {
            LOGGER.info("Sending a message to the Queue");
            AmazonQueue.getQueue().sendMessage(new SendMessageRequest(QUEUE_URL, message));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error Occurred while sending a message to SQS Queue", ex);
        }
    }

    public static List<Message> getMessage() {
        List<Message> messages = null;
        try {
            LOGGER.info("Getting a message from the Queue");
            AmazonSQS queue = AmazonQueue.getQueue();
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(QUEUE_URL);
            receiveMessageRequest.setMaxNumberOfMessages(1);
            messages = queue.receiveMessage(receiveMessageRequest).getMessages();
            if (messages.size() == 0){
                LOGGER.info("The Queue is Empty!");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error Occurred while sending a message to SQS Queue", ex);
        }
        return messages;
    }

    public static void deleteMessage(Message message){
        try {
            LOGGER.info("Deleting a message from the Queue");
            AmazonSQS queue = AmazonQueue.getQueue();
            DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(QUEUE_URL, message.getReceiptHandle());
            queue.deleteMessage(deleteMessageRequest);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error Occurred while Deleting a message to SQS Queue", ex);
        }
    }


}
