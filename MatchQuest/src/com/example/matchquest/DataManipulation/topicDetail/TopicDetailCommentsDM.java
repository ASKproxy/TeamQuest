package com.example.matchquest.DataManipulation.topicDetail;

import java.util.ArrayList;
import java.util.List;

import com.example.matchquest.model.Comment;
import com.example.matchquest.model.Players;

public class TopicDetailCommentsDM {
	
	public List<Comment> getComments(String topicId,String teamId)
	{
		List<Comment> commentList = new ArrayList<Comment>();
		for(int i = 0 ; i < 5 ; i ++)
		{
			Comment comment = new Comment();
			comment.setComment("comment comm " + i);
				Players player = new Players();
				player.setPlayerId("955159116" + i);
				player.setPlayerName("playername" + i);
				player.setRegistered((i%2 == 0) ? true : false);
			
			comment.setPlayer(player);
			comment.setTopicId(topicId);
			comment.setTeamId(teamId);
			commentList.add(comment);
			
		}
		
		return commentList;
	}
	
	public void saveComment(String teamId, String topicId,Comment comment){
		
	}

}
