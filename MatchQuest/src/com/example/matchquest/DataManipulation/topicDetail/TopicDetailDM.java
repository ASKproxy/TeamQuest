package com.example.matchquest.DataManipulation.topicDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.matchquest.common.TeamQuestConstants;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.Team;
import com.example.matchquest.model.TopicDetails;

public class TopicDetailDM {

	public List<TopicDetails> getTopicDetailTeam(String uniqueId )
	{
		List<TopicDetails> topicDetailList = new ArrayList<TopicDetails>();
		for(int i = 1 ;i < 5 ; i++)
		{
			TopicDetails topicDetail = new TopicDetails();
			topicDetail.setUniqueId(uniqueId);
			topicDetail.setTopicId("topic id"  + i);
			topicDetail.setTopic("topic " + i);
			topicDetail.setOption("option " + i);
			topicDetail.setCategory(i%2 ==0 ? TeamQuestConstants.general_key : TeamQuestConstants.nextgame_key);
			Map<String,List<Players>> optionDetail = new LinkedHashMap<String, List<Players>>();
			Map<String,String> optionIds = new LinkedHashMap<String, String>();
			for(int j = 0 ;j < i ; j++)
			{
				Players p1 = new Players();
				p1.setPlayerId("955159116" + j);
				Players p2 = new Players();
				p2.setPlayerId("955159116" + j+1);
				optionIds.put("option id" +j+""+i, "option " +j);
				optionDetail.put("option id" +j+""+i, new ArrayList<Players>(Arrays.asList(p1,p2)));
				
			}
			topicDetail.setOptionIds(optionIds);
			topicDetail.setOptions(optionDetail);
			topicDetail.setCreatedBy("created by" + i);
			topicDetailList.add(topicDetail);
		}
		return topicDetailList;
	}
	
	public List<TopicDetails> getTopicDetailMatch(String uniqueId )
	{
		List<TopicDetails> topicDetailList = new ArrayList<TopicDetails>();
		for(int i = 1 ;i < 5 ; i++)
		{
			TopicDetails topicDetail = new TopicDetails();
			topicDetail.setUniqueId(uniqueId);
			topicDetail.setTopicId("match topic id"  + i);
			topicDetail.setTopic("match topic " + i);
			topicDetail.setOption("match option " + i);
			topicDetail.setCategory(i%2 ==0 ? TeamQuestConstants.general_key : TeamQuestConstants.matchdetail_key);
			Map<String,List<Players>> optionDetail = new LinkedHashMap<String, List<Players>>();
			Map<String,String> optionIds = new LinkedHashMap<String, String>();
			for(int j = 0 ;j < i ; j++)
			{
				Players p1 = new Players();
				p1.setPlayerId("955159116" + j);
				Players p2 = new Players();
				p2.setPlayerId("955159116" + j+1);
				optionIds.put("match option id" +j+""+i, "match option " +j);
				optionDetail.put("match option id" +j+""+i, new ArrayList<Players>(Arrays.asList(p1,p2)));
				
			}
			topicDetail.setOptionIds(optionIds);
			topicDetail.setOptions(optionDetail);
			topicDetail.setCreatedBy("created by" + i);
			topicDetailList.add(topicDetail);
		}
		return topicDetailList;
	}
	
	public TopicDetails saveDetail(TopicDetails topicDetail,TopicDetails modifiedTopicDetail,String playerId)
	{
		TopicDetails savedTopicDetail = new TopicDetails();
		savedTopicDetail.setUniqueId(topicDetail.getUniqueId());
		savedTopicDetail.setTopic(topicDetail.getTopic());
		savedTopicDetail.setTopicId(topicDetail.getTopicId());
		savedTopicDetail.setCategory(topicDetail.getCategory());
		savedTopicDetail.setCreatedBy(topicDetail.getCreatedBy());
		savedTopicDetail.setOption("opation a");
		savedTopicDetail.setOptionIds(topicDetail.getOptionIds());
		for(Entry<String, List<Players>> entry : topicDetail.getOptions().entrySet())
		{
			List<Players> playersList = new ArrayList<Players>();
			for(Players p : entry.getValue())
			{
				if(!p.getPlayerId().equals(playerId))
				{
					playersList.add(p);
				}
			}
			if(modifiedTopicDetail.getOptionModified().get(entry.getKey()).equals(Boolean.FALSE))
			{
				savedTopicDetail.getOptions().put(entry.getKey(), playersList);
			}else if(modifiedTopicDetail.getOptionModified().get(entry.getKey()).equals(Boolean.TRUE)){
				Players player = new Players();
				player.setPlayerId(playerId);
				playersList.add(player);
				savedTopicDetail.getOptions().put(entry.getKey(), playersList);
			}
		}
		
		return savedTopicDetail;
	}
	
	public int addTopic(String uniqueId, String playerId, List<String> options,String topic,String screenName)
	{
//		unique id can match status id or team id topic id should be created newly
		
//		based on screen name we have update in team detail or match detail table
		return 1;
	}
	
	public int updateTopic(TopicDetails updateTopicDetail,String screenName)
	{
//		unique id from topicdetail can be match status id or team id topic id should be created newly
//		topic id , topic ,optionid's , created by can be taken from updatetopicdetail object
		
//		in optionsid map if id  already exist in DB then update the option value or else insert that option with new option id
		
//		based on screen name we have update in team detail or match detail table
		
		return 1;
	}
	
}
