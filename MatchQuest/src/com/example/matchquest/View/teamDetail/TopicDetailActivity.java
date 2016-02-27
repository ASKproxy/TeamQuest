package com.example.matchquest.View.teamDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchquest.R;
import com.example.matchquest.DataManipulation.topicDetail.TopicDetailCommentsDM;
import com.example.matchquest.DataManipulation.topicDetail.TopicDetailDM;
import com.example.matchquest.SQLiteData.TeamQuestSqlite;
import com.example.matchquest.View.CommonActivity;
import com.example.matchquest.View.CommonViewClass;
import com.example.matchquest.View.NetworkChangeReceiverActvity;
import com.example.matchquest.common.Details;
import com.example.matchquest.common.TeamQuestConstants;
import com.example.matchquest.model.Comment;
import com.example.matchquest.model.Players;
import com.example.matchquest.model.RequestStatus;
import com.example.matchquest.model.Team;
import com.example.matchquest.model.TopicDetails;

public class TopicDetailActivity  extends CommonActivity{
	
	
	ScrollView mainScrollView;
	RelativeLayout questAnsLayout;
	LinearLayout commentsEditViewLayout;
	
	LinearLayout commentsLayout;
	ScrollView commentsScrollView;
	TextView topicTextView;
	Button saveOptionButton;
	ImageView updateButton;
	
	View optionsView;
	TextView topicOption;
	CheckBox optionCheckBox;
		
	Players player;
	
	EditText commentEditText;
	ImageView suggestButton;
	
	TextView playerNameTextView;
	TextView commentTextView;
	LinearLayout commentsLinearLayout;
	View seperatorView;
	
	ImageView fullScreenIcon;
	ImageView normalScreenIcon;
	int lastIconVisibleId;
	
	TopicDetailActivity topicDetailActivity;
	TopicDetails topicDetail;
	
	LinearLayout optionDetailLayout;
	
	Team team;
	RequestStatus requestStatus;
	String screen;
	String uniqueId;
	
	int height;
	int screenHeight;
	int commentEditTextHeight;
	int questAnsLayoutHeight;
	
	TopicDetailCommentsDM topicDetailCommentDM;
	List<Comment> commentList = new ArrayList<Comment>();
	
	boolean initalLoad = false;
	
	TeamQuestSqlite teamDetailSqlite;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quest_ans_layout);
		
		mainScrollView = (ScrollView) findViewById(R.id.main_scroll_view);
		mainScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
			    Rect r = new Rect();
			    //r will be populated with the coordinates of your view that area still visible.
			    mainScrollView.getWindowVisibleDisplayFrame(r);

			    int heightDiff = mainScrollView.getRootView().getHeight() - (r.bottom - r.top);
			    if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
			    	if(fullScreenIcon.getId() == lastIconVisibleId)
			    	{
			    		if(fullScreenIcon.getVisibility() == View.VISIBLE)
			    		{
			    		questAnsLayout.setVisibility(View.GONE);
			    		fullScreenIcon.setVisibility(View.GONE);
			    		int scrollHeight = (r.bottom - r.top) - commentEditTextHeight - getSupportActionBar().getHeight() - 70;
						commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (scrollHeight)));
			    		}
			    	}else if(normalScreenIcon.getId() == lastIconVisibleId)
			    	{
			    		if(normalScreenIcon.getVisibility() == View.VISIBLE)
			    		{
			    		normalScreenIcon.setVisibility(View.GONE);
			    		int scrollHeight = (r.bottom - r.top) - commentEditTextHeight - getSupportActionBar().getHeight() - 70;
						commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (scrollHeight)));
			    		}
			    		
			    	}
			    	
			    }else{
			    	if(fullScreenIcon.getId() == lastIconVisibleId)
			    	{
			    		if(fullScreenIcon.getVisibility() == View.GONE)
			    		{
			    		questAnsLayout.setVisibility(View.VISIBLE);
			    		fullScreenIcon.setVisibility(View.VISIBLE);
			    		commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (height-50)));
			    		}
			    	}else if(normalScreenIcon.getId() == lastIconVisibleId)
			    	{
			    		if(normalScreenIcon.getVisibility() == View.GONE)
			    		{
			    		normalScreenIcon.setVisibility(View.VISIBLE);
			    		commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (screenHeight-60)));
			    		}
			    		
			    	}
			    }
			    scrollDown();
			 }
			}); 		
		topicDetailActivity =this;
		player = Details.getPlayer(this);
        team =  (Team) getIntent().getSerializableExtra(TeamQuestConstants.team_key);
        requestStatus = (RequestStatus) getIntent().getSerializableExtra(TeamQuestConstants.requestStatus_key);
        screen = getIntent().getStringExtra(TeamQuestConstants.screenName_key);
        if(screen.equals(TeamQuestConstants.teamDetailScreen_key))
        {
        	uniqueId = team.getTeamId();
        }else if (screen.equals(TeamQuestConstants.matchDetailScreen_key))
        {
        	uniqueId = requestStatus.getUniqueId();
        }
        if(team != null)
        {
        	Details.setTeam(team);
        }else{
        	team = Details.getTeam();
        }
		topicDetail = (TopicDetails) getIntent().getSerializableExtra(TeamQuestConstants.topicDetail_key);
		
		topicTextView = (TextView) findViewById(R.id.quest_ans_topic_text);
		saveOptionButton = (Button) findViewById(R.id.quest_ans_save_button);
		updateButton = (ImageView) findViewById(R.id.quest_ans_edit_button);

		topicTextView.setText(topicDetail.getTopic());
		saveOptionButton.setEnabled(false);
		updateButton.setVisibility(View.GONE);
		if(team.getCaptain().equals(player.getPlayerId()) || team.getViceCaptain().equals(player.getPlayerId()) || topicDetail.getCreatedBy().equals(player.getPlayerId()))
		{
			updateButton.setVisibility(View.VISIBLE);
		}
		
		fullScreenIcon = (ImageView) findViewById(R.id.full_screen_icon);
		normalScreenIcon = (ImageView) findViewById(R.id.normal_screen_icon);
		lastIconVisibleId = R.id.full_screen_icon;
		
		optionDetailLayout = (LinearLayout) findViewById(R.id.quest_ans_options_layout);
		loadOptions();
		
		questAnsLayout = (RelativeLayout) findViewById(R.id.quest_ans_layout);
		
		commentsEditViewLayout = (LinearLayout) findViewById(R.id.quest_ans_edit_text_layout);
		
		commentsLayout = (LinearLayout) findViewById(R.id.quest_ans_comment_layout);
		commentsScrollView = (ScrollView) findViewById(R.id.quest_ans_scroll_view);
		
		teamDetailSqlite = new TeamQuestSqlite(getApplicationContext());
		commentList.clear();
		commentList.addAll(teamDetailSqlite.getTeamDetailSqlite().getComments(uniqueId,team, topicDetail.getTopicId()));
		updateView();
		registernetworkListener(false);
		
		commentEditText = (EditText) findViewById(R.id.quest_ans_edit_text);
		suggestButton = (ImageView) findViewById(R.id.quest_ans_suggest_sendicon);
		
		
		setListeners();
		initalLoad = true;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!initalLoad)
		{
			if(getNetworkChangeReceiverActvity()!= null)
			{
			getNetworkChangeReceiverActvity().updateData();
			}
			
		}
		initalLoad = false;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	        finish();
	        return true;
	}
	    return false;
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		screenHeight = mainScrollView.getHeight();
		commentEditTextHeight = commentsEditViewLayout.getHeight();
		questAnsLayoutHeight = questAnsLayout.getHeight();
		
		height = screenHeight - questAnsLayoutHeight;
		screenHeight = screenHeight - getSupportActionBar().getHeight();
		
		height = height - commentEditTextHeight;
		
		height = height - findViewById(R.id.quest_ans_suggestion_text).getHeight();
		
		commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height-50));
	}
	private void loadOptions()
	{
		optionDetailLayout.removeAllViews();
		saveOptionButton.setEnabled(false);
		int j = 0;
		for(final Entry<String, String> entry : topicDetail.getOptionIds().entrySet())
		{
		optionsView = getLayoutInflater().inflate(R.layout.topic_detail_option_layout, null);
//		optionsView.setBackgroundColor(Color.WHITE);
		topicOption = (TextView) optionsView.findViewById(R.id.topic_options);
		final TextView topicOptionDetail = (TextView) optionsView.findViewById(R.id.topic_options_detail);
		
		optionCheckBox = (CheckBox) optionsView.findViewById(R.id.topic_options_checkbox);
		optionCheckBox.setTag(entry.getKey());
		optionCheckBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				boolean flag = false;
				String optionId = (String) cb.getTag();
				if(cb.isChecked())
				{
					if(topicDetail.getOptionOriginal().get(optionId).equals(Boolean.TRUE))
					{
						topicOptionDetail.setText((topicDetail.getOptions().get(entry.getKey()).size() )+ " players choosed this");						
					}else{
						topicOptionDetail.setText((topicDetail.getOptions().get(entry.getKey()).size()+1 )+ " players choosed this");
					}
				}else{
					if(topicDetail.getOptionOriginal().get(optionId).equals(Boolean.TRUE))
					{
						topicOptionDetail.setText((topicDetail.getOptions().get(entry.getKey()).size() -1)+ " players choosed this");						
					}else{
						topicOptionDetail.setText((topicDetail.getOptions().get(entry.getKey()).size())+ " players choosed this");
					}				
				}
				topicDetail.getOptionModified().put( optionId, cb.isChecked());
				for(Entry<String, Boolean> optionEntry :topicDetail.getOptionModified().entrySet())
				{
					if(!optionEntry.getValue().equals(topicDetail.getOptionOriginal().get(optionEntry.getKey())))
					{
						flag = true;
						break;
					}
				}
				
				saveOptionButton.setEnabled(flag);
				
				
			}
		});
		topicDetail.getOptionModified().put(entry.getKey(), false);
		topicDetail.getOptionOriginal().put(entry.getKey(), false);
		for(Players player : topicDetail.getOptions().get(entry.getKey()))
		{
			if(player.getPlayerId().equals(Details.getPlayer(this).getPlayerId()))
			{
				optionCheckBox.setChecked(true);

				topicDetail.getOptionModified().put(entry.getKey(), true);
				topicDetail.getOptionOriginal().put(entry.getKey(), true);
				break;
			}
		}
		
		
		topicOption.setText(entry.getValue());
		topicOptionDetail.setText(topicDetail.getOptions().get(entry.getKey()).size() + " players choosed this");
		optionDetailLayout.addView(optionsView,j);
		j++;
		}
	
	}
	private void setListeners()
	{
		
		saveOptionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(CommonViewClass.isNetworkAvailable(getApplicationContext()))
				{
					TopicDetailDM saveOption = new TopicDetailDM();
					TopicDetails savedTopicDetail = saveOption.saveDetail(topicDetail, topicDetail, player.getPlayerId());
					topicDetail.getOptionOriginal().clear();
					topicDetail.getOptionOriginal().putAll(topicDetail.getOptionModified());
					topicDetail.setOptions(savedTopicDetail.getOptions());
					topicDetail.setOptionIds(savedTopicDetail.getOptionIds());
					loadOptions();
					Toast.makeText(getApplicationContext(), "saved", 1000).show();
				}
				else{
					CommonViewClass.showdialog(TopicDetailActivity.this, TeamQuestConstants.connectToInternet_key);
				}
			}
		});
	
	
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent teamEditIntent = new Intent(getApplicationContext(), AddTopicActivity.class);
				if(screen.equals(TeamQuestConstants.teamDetailScreen_key))
				{
					teamEditIntent.putExtra("Team", team);  
				}else if(screen.equals(TeamQuestConstants.matchDetailScreen_key))
				{
					teamEditIntent.putExtra(TeamQuestConstants.requestStatus_key, requestStatus);
				}
				teamEditIntent.putExtra(TeamQuestConstants.toupdate_key, true);
				teamEditIntent.putExtra(TeamQuestConstants.topicDetail_key, topicDetail);
				teamEditIntent.putExtra(TeamQuestConstants.screenName_key, screen);
				startActivity(teamEditIntent);
			}
		});

		fullScreenIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fullScreenIcon.setVisibility(View.GONE);
				normalScreenIcon.setVisibility(View.VISIBLE);
				lastIconVisibleId = normalScreenIcon.getId();
				questAnsLayout.setVisibility(View.GONE);
				commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeight-60));
				
			}
		});
		
		normalScreenIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				normalScreenIcon.setVisibility(View.GONE);
				fullScreenIcon.setVisibility(View.VISIBLE);
				lastIconVisibleId = fullScreenIcon.getId();
				questAnsLayout.setVisibility(View.VISIBLE);
				commentsScrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height-50));
			}
		});

		suggestButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(CommonViewClass.isNetworkAvailable(getApplicationContext()))
				{
				if(commentEditText.getText()!= null && !commentEditText.getText().toString().equals(""))
				{
				addCommentsLayout(player,commentEditText.getText().toString().trim());
				
				scrollDown();
				
				
				if(teamDetailSqlite ==  null)
				{
				teamDetailSqlite = new TeamQuestSqlite(getApplicationContext());
				}
				Comment comment = new Comment();
				comment.setComment(commentEditText.getText().toString().trim());
				comment.setTeamId(uniqueId);
				comment.setTopicId(topicDetail.getTopicId());
				comment.setPlayer(player);
				
				TopicDetailCommentsDM topicDetailCommentDM = new TopicDetailCommentsDM();
				topicDetailCommentDM.saveComment(uniqueId, topicDetail.getTopicId(), comment);
				
				teamDetailSqlite.getTeamDetailSqlite().
					insertTopicDetailComment(uniqueId, topicDetail.getTopicId(), false, new ArrayList<Comment>(Arrays.asList(comment)));
				commentEditText.setText("");
				}
				}
				else{
					CommonViewClass.showdialog(TopicDetailActivity.this, TeamQuestConstants.connectToInternet_key);
				}
			}
		});

		scrollDown();
	}
	
	private void scrollDown()
	{
		commentsScrollView.post(new Runnable() {
		    @Override
		    public void run() {
		    	commentsScrollView.fullScroll(View.FOCUS_DOWN);
		    }
		});
	}
	
	private void addCommentsLayout(Players player,String comments)
	{
		commentsLinearLayout = new LinearLayout(getApplicationContext());
		commentsLinearLayout.setPadding(8, 8, 8, 8);
		commentsLinearLayout.setOrientation(LinearLayout.VERTICAL);
		commentsLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		commentTextView = new TextView(getApplicationContext());
		commentTextView.setId(R.id.commentTextView);
		commentTextView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		commentTextView.setPadding(20, 0, 0, 0);
		commentTextView.setTypeface(Typeface.SANS_SERIF);

		commentTextView.setTextSize(20);
		commentTextView.setTextColor(Color.BLACK);
		commentTextView.setText(comments);
		
		playerNameTextView = new TextView(getApplicationContext());
		playerNameTextView.setId(R.id.playerNameTextView);
		playerNameTextView.setTextColor(Color.BLUE);
		playerNameTextView.setTextSize(15);
		playerNameTextView.setTypeface(Typeface.SANS_SERIF);
		playerNameTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		if(player.getPlayerId().equals(Details.getPlayer(this).getPlayerId()))
		{
			playerNameTextView.setText("You");
		}else{
			playerNameTextView.setText(player.getPlayerName());
		}
		
		seperatorView = new View(getApplicationContext());
		seperatorView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT , 1));
		seperatorView.setBackgroundColor(Color.GRAY);

		commentsLinearLayout.addView(playerNameTextView);
		commentsLinearLayout.addView(commentTextView);
		commentsLinearLayout.addView(seperatorView);
		
		commentsLayout.addView(commentsLinearLayout);
	}

	@Override
	protected void loadInBackGround() {
		topicDetailCommentDM = new TopicDetailCommentsDM();
		commentList.clear();
		commentList.addAll(topicDetailCommentDM.getComments(topicDetail.getTopicId(), uniqueId));
		if(teamDetailSqlite ==  null)
		{
		teamDetailSqlite = new TeamQuestSqlite(getApplicationContext());
		}
		teamDetailSqlite.getTeamDetailSqlite().insertTopicDetailComment(uniqueId, topicDetail.getTopicId(), true, commentList);

		
	}

	@Override
	protected void updateView() {
		commentsLayout.removeAllViews();
		for(Comment comment : commentList)
		{
			addCommentsLayout(comment.getPlayer(), comment.getComment());
		}
	}
}
