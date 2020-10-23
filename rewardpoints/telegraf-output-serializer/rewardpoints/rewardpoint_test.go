package rewardpoints

import (
	"fmt"
	"strconv"
	"testing"
	"time"

	"github.com/influxdata/telegraf"
	"github.com/influxdata/telegraf/metric"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func MustMetric(v telegraf.Metric, err error) telegraf.Metric {
	if err != nil {
		panic(err)
	}
	return v
}

func TestSerializeMetricFloat(t *testing.T) {
	now := time.Now()
	tags := map[string]string{
		"owner_id":         "1",
		"description":      "sample",
		"points":           "50",
		"rewardpoint_date": "2020/10/22",
	}
	fields := map[string]interface{}{
		"description":      "sample",
		"rewardpoint_date": "2020/10/22",
		"points":           int(50),
		"owner_id":         int(1),
	}
	m, err := metric.New("sample", tags, fields, now)
	assert.NoError(t, err)

	s, _ := NewSerializer()
	var buf []byte
	buf, err = s.Serialize(m)
	assert.NoError(t, err)
	timestamp := (now.UnixNano() / int64(time.Millisecond))
	i, err := strconv.ParseInt(fmt.Sprint(timestamp), 10, 64)
	if err == nil {
		tm := time.Unix(i, 0)
		date := fmt.Sprintf("%d/%02d/%02d", tm.Year(), tm.Month(), tm.Day())
		fmt.Printf("%s", date)
	}
	expS := []byte(fmt.Sprintf(`[{"owner_id":1,"description":"sample","points":50,"rewardpoint_date":"2020/10/22"}]`))
	assert.Equal(t, string(expS), string(buf))
}

func TestSerializeBatch(t *testing.T) {
	m := MustMetric(
		metric.New(
			"sample",
			map[string]string{
				"owner_id":         "1",
				"description":      "sample",
				"points":           "100",
				"rewardpoint_date": "2020/10/22",
			},
			map[string]interface{}{
				"description":      "sample",
				"rewardpoint_date": "2020/10/22",
				"points":           int(50),
				"owner_id":         int(1),
			},
			time.Unix(0, 0),
		),
	)

	metrics := []telegraf.Metric{m, m}
	s, _ := NewSerializer()
	buf, err := s.SerializeBatch(metrics)
	require.NoError(t, err)
	fmt.Print("buf+" + string(buf))
	require.Equal(t, []byte(`[{"owner_id":1,"description":"sample","points":100,"rewardpoint_date":"2020/10/22"},{"owner_id":1,"description":"sample","points":100,"rewardpoint_date":"2020/10/22"}]`), buf)
}
