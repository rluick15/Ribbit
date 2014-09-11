package com.richluick.ribbit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.richluick.ribbit.utils.ParseConstants;
import com.richluick.ribbit.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rich on 9/5/2014.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mMessages;
    protected String mMilliString;

    public MessageAdapter(Context context, List<ParseObject> messages) {
        super(context, R.layout.message_item, messages);

        mContext = context;
        mMessages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.messageIcon);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.senderLabel);
            holder.dateLabel = (TextView) convertView.findViewById(R.id.dateLabel);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject message = mMessages.get(position);

        if(message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
            holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
        }
        else {
            holder.iconImageView.setImageResource(R.drawable.ic_action_play_over_video);
        }

        String date = formatDate(message);

        holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
        holder.dateLabel.setText(date);

        return convertView;
    }

    private String formatDate(ParseObject message) {
        Date messageDate = message.getCreatedAt();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(messageDate);
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
        TextView dateLabel;
    }

    public void refill(List<ParseObject> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }

}
