# RewardPoints: The outline of an automated monetization of recognition rewards

**Problem Statement:** Employees in an organization work from home and rely on applications like Slack to remain connected to the workplace. They use emojis to recognize each other’s hard work. Employers would like to leverage this peer recognition to be automatically translated to monetary rewards for the recipients. This should not require any extra action or change in the habit of the recipient in their daily routine.

**Solution:** Badges and emojis are staple forms of communication language between team members of an organization. They are immediate, informal, and peer-reviewed forms of recognition. Up until now, any mechanism of translating peer recognition to rewards used to involve a laborious process disrupting both the sender and the receiver's routine which tends to elevate the barrier to rewards and their receipt. The following mechanism introduces inline automation to translate the recognition events to reward points which are eventually cut out to gift cards and sent to the recipients.

This automation includes a sender and a receiver side.  The sender introduces a bot user to all the monitored Slack channels. This bot user subscribes to an event loop using an [Event API](https://api.slack.com/events-api#receiving_events) available from Slack and follows the event-driven sequence. A user posts an emoji for recognition of another user and an event of type ‘reaction_added’ along with attributes such as ‘item_user’ who is the recipient of the recognition. The bot user receives the event and responds within three seconds. The event loop guarantees to post the event with grace, security, respect, and retry.  The bot user handles each event notification with a light-weight technical implementation that treats each event to be of equal value in terms of reward points. Each event notification is translated to a reward point creation request in a reward point accumulation service that maintains a ledger of owner and reward points.

The automation on the receiver side targets the redeeming of the reward points accumulated by a user. Each distinct recipient receives an aggregation of the reward points accumulated periodically. When the reward points exceed a threshold, an eGift card from a major online retailer is cut out using the [eGifter API](https://corporate.egifter.com/gift-card-api/). The code generated for the eGift card from the redeemed reward points is mailed out to the email address retrieved from Slack for that recipient.  Since the codes are dynamic and the emails are delivered to the recipient’s email inbox, the sender and receiver take no additional actions for getting reward points.

Since the event metadata gives enough visibility on the policies associated with qualifying an event to the reward points creation such as maximum possible rewards from one user to another in a day, the reward points generation can be controlled and free from malpractice.

The implementation for this monetization of an automated peer recognition system is outlined here: https://github.com/ravibeta/RewardPoints

Further reading on scaling up these services to arbitrary usage and size of an organization is included [here](https://1drv.ms/w/s!Ashlm-Nw-wnWwg-a52lDQkwQIQnl?e=0tGzIg).

Thank you
