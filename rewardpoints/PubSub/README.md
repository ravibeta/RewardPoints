### The design of a publisher-subscriber system for reward points: 

*Introduction*: Each reward points entry is of interest to multiple subscribers. Stores that honor reward points can redefine their selections available to the customer for their renewed tally of reward points. Reporting services can update the charts and graphs. These use cases require a pub-sub model for the publisher-subscriber  

*Description*: The reward points accumulation [service](https://1drv.ms/w/s!Ashlm-Nw-wnWwg-a52lDQkwQIQnl?e=4LiBui) is an online transaction processing system that does not differentiate rewards point records created from partners. An offline pub-sub model is required that can validate/invalidate entries generated from clients and notify interested subscribers the same so that those subscribers can be more purpose driven. Generating notifications at scope and levels of user and reward point hierarchy, enables subscribers to focus more on their purpose rather than bookkeeping and querying the reward points.  

The publisher-subscriber model is responsible for admission control and notifications. By generalizing the accumulation service to be a publisher, this service can allow publishers and subscribers to scale independent of the data and make them more efficient by subscribing to events of interest.  

The use of a message broker or a queue, serializer and deserializer for notifications can be sufficient for scalable storage but the notification system is also responsible for pushing them out. This handling of the event is at the core of the pub-sub model. 

This events service implements both the watcher and the push notifications. The programmable interface includes the following methods: 

An Observable.FromEvent<TDelegate, TEventArgs> method that returns an IObservable sequence from the record/event. An IObservable interface sends the notification. 

An IObservable interface that has a  

- Subscribe method with the provider parameter to subscribe to notifications and unsubscribe method 

- An onNext method to handle the processing. 

- An onError method for error conditions. 

- An onCompleted method to unsubscribe from future notifications. 

An IObserver interface that receives the notification and includes methods just like above. The methods are similar on both the sender and receiver side since they each must handle a notification except that one sends it and the other receives it. It is just that their roles are opposite in that they attach and detach from each other. The observer (subscriber) attaches and detaches itself to the provider (publisher). 

 

The Event notification service is independent of the accumulation and redeeming services that credit and debit reward points. This service allows clients to register the callback method and is best for handling clients that are registered and known to be receptive. 

 

The event notification service has a manager for all the publishers to partition the event records and send notifications in a re-entrant manner. After the notifications have been sent for the event, it is not reprocessed. 

 

 

 
