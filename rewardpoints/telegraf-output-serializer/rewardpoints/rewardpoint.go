package rewardpoints

import (
	"bytes"
	"encoding/json"
	"fmt"
	"strconv"
	"time"

	"github.com/influxdata/telegraf"
)

type serializer struct {
	TimestampUnits time.Duration
}

type OIMetric struct {
	OwnerId     int    `json:"owner_id"`
	Description string `json:"description"`
	Points      int    `json:"points"`
	Date        string `json:"rewardpoint_date"`
}

type OIMetrics []OIMetric

func NewSerializer() (*serializer, error) {
	s := &serializer{}
	return s, nil
}

func (s *serializer) Serialize(metric telegraf.Metric) (out []byte, err error) {
	serialized, err := s.createObject(metric)
	if err != nil {
		fmt.Printf("%v", err)
		return []byte{}, nil
	}
	return serialized, err
}

func (s *serializer) SerializeBatch(metrics []telegraf.Metric) (out []byte, err error) {
	objects := make([]byte, 0)
	for _, metric := range metrics {
		m, err := s.createObject(metric)
		if err != nil {
			return nil, fmt.Errorf("D! [serializer.rewardpoints] Dropping invalid metric: %s", metric.Name())
		} else if m != nil {
			objects = append(objects, m...)
		}
	}
	replaced := bytes.Replace(objects, []byte("]["), []byte(","), -1)
	return replaced, nil
}

func (s *serializer) createObject(metric telegraf.Metric) ([]byte, error) {
	/*  RewardPoints accumulation service supports an array of JSON objects.
		** Following elements accepted in the request body:
			 ** owner_id: 	        The owner of the metric
			 ** description:   	The description of the metric
			 ** points:       	The value of the reward points
			 ** rewardpoint_date:   The date for the grant of reward points in yyyy/MM/dd format
	           ** Note metric is time-series data and must include timestamp. In this timestamp is already converted to rewardpoint_date
	*/
	var allmetrics OIMetrics
	var oimetric OIMetric

	// Process Tags to extract node & resource name info
	for _, tag := range metric.TagList() {
		if tag.Key == "" || tag.Value == "" {
			continue
		}

		if tag.Key == "owner_id" {
			oimetric.OwnerId, _ = strconv.Atoi(tag.Value)
		}

		if tag.Key == "description" {
			oimetric.Description = tag.Value
		}

		if tag.Key == "points" {
			oimetric.Points, _ = strconv.Atoi(tag.Value)
		}

		if tag.Key == "rewardpoint_date" {
			oimetric.Date = tag.Value
		}
	}
	allmetrics = append(allmetrics, oimetric)

	metricsJson, err := json.Marshal(allmetrics)

	return metricsJson, err
}

