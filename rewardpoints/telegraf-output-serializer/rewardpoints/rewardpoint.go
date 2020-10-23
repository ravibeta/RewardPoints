// package rewardpoints
// 
// import (
// 	"bytes"
// 	"encoding/json"
// 	"fmt"
// 	"time"
// 
// 	"github.com/influxdata/telegraf"
// )
// 
// type serializer struct {
// 	TimestampUnits time.Duration
// }
// 
// type OIMetric struct {
//         OwnerId      int64              `json:"owner_id"`
// 	Description  string             `json:"description"`
//         Points       string             `json:"points"`
// 	Date         string             `json:"rewardpoint_date"`
// }
// 
// type OIMetrics []OIMetric
// 
// func NewSerializer() (*serializer, error) {
// 	s := &serializer{}
// 	return s, nil
// }
// 
// func (s *serializer) Serialize(metric telegraf.Metric) (out []byte, err error) {
// 	serialized, err := s.createObject(metric)
// 	if err != nil {
// 		return []byte{}, nil
// 	}
// 	return serialized, err
// }
// 
// func (s *serializer) SerializeBatch(metrics []telegraf.Metric) (out []byte, err error) {
// 	objects := make([]byte, 0)
// 	for _, metric := range metrics {
// 		m, err := s.createObject(metric)
// 		if err != nil {
// 			return nil, fmt.Errorf("D! [serializer.rewardpoints] Dropping invalid metric: %s", metric.Name())
// 		} else if m != nil {
// 			objects = append(objects, m...)
// 		}
// 	}
// 	replaced := bytes.Replace(objects, []byte("]["), []byte(","), -1)
// 	return replaced, nil
// }
// 
// func (s *serializer) createObject(metric telegraf.Metric) ([]byte, error) {
// 	/*  RewardPoints accumulation service supports an array of JSON objects.
// 	** Following elements accepted in the request body:
// 		 ** owner_id: 	        The owner of the metric
// 		 ** description:   	The description of the metric
// 		 ** points:       	The value of the reward points
// 		 ** rewardpoint_date:   The date for the grant of reward points in yyyy/MM/dd format
// 	*/
// 	var allmetrics OIMetrics
// 	var oimetric OIMetric
// 
// 	oimetric.Source = "Telegraf"
// 
// 	// Process Tags to extract node & resource name info
// 	for _, tag := range metric.TagList() {
// 		if tag.Key == "" || tag.Value == "" {
// 			continue
// 		}
// 
// 		if tag.Key == "owner_id" {
// 			oimetric.OwnerId = tag.Value
// 		}
// 
// 		if tag.Key == "description" {
// 			oimetric.Description = tag.Value
// 		}
// 
//                 if tag.Key == "points" {
//                         oimetric.Points = tag.Value
//                 }
// 
//                 if tag.Key == "rewardpoint_date" {
//                         oimetric.Date = tag.Value
//                 }
// 	}
// 
// 	// Format timestamp to UNIX epoch
// 	timestamp := (metric.Time().UnixNano() / int64(time.Millisecond))
//         oimetric.Date =  fmt.Sprintf("%d/%02d/%02d", timestamp.Year(), timestamp.Month(), timestamp.Day())
// 
// 	// Loop of fields value pair and build datapoint for each of them
// 	for _, field := range metric.FieldList() {
// 		if !verifyValue(field.Value) {
// 			// Ignore String
// 			continue
// 		}
// 
// 		if field.Key == "" {
// 			// Ignore Empty Key
// 			continue
// 		}
// 
// 		oimetric.Description = field.Key
// 		oimetric.Points = field.Value
// 
// 		allmetrics = append(allmetrics, oimetric)
// 	}
// 
// 	metricsJson, err := json.Marshal(allmetrics)
// 
// 	return metricsJson, err
// }
// 
// func verifyValue(v interface{}) bool {
// 	switch v.(type) {
// 	case string:
// 		return false
// 	}
// 	return true
// }
