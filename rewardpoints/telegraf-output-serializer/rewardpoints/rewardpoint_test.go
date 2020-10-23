// package rewardpoints
// 
// import (
// 	"fmt"
// 	"sort"
// 	"testing"
// 	"time"
// 
// 	"github.com/influxdata/telegraf"
// 	"github.com/influxdata/telegraf/metric"
// 	"github.com/stretchr/testify/assert"
// 	"github.com/stretchr/testify/require"
// )
// 
// func MustMetric(v telegraf.Metric, err error) telegraf.Metric {
// 	if err != nil {
// 		panic(err)
// 	}
// 	return v
// }
// 
// func TestSerializeMetricFloat(t *testing.T) {
// 	now := time.Now()
// 	tags := map[string]string{
//                 "owner_id": "1",
// 		"description": "sample",
//                 "points": 50,
//                 "rewardpoint_date": "2020/10/22",
// 	}
// 	fields := map[string]interface{}{
//                 "description": "sample",
//                 "rewardpoint_date": "2020/10/22",
// 		"points": int64(50),
//                 "owner_id": int64(1),
// 	}
// 	m, err := metric.New("sample", tags, fields, now)
// 	assert.NoError(t, err)
// 
// 	s, _ := NewSerializer()
// 	var buf []byte
// 	buf, err = s.Serialize(m)
// 	assert.NoError(t, err)
//         timestamp := (now.UnixNano() / int64(time.Millisecond))
//         date := fmt.Sprintf("%d/%02d/%02d", timestamp.Year(), timestamp.Month(), timestamp.Day())
// 	expS := []byte(fmt.Sprintf(`[{"owner_id":1,"description":"sample","points":50,"date":"2020/10/22"}]`))
// 	assert.Equal(t, string(expS), string(buf))
// }
// 
// 
// func TestSerializeMetricString(t *testing.T) {
// 	now := time.Now()
//         tags := map[string]string{
//                 "owner_id": "1",
//                 "description": "sample",
//                 "points": 50,
//                 "rewardpoint_date": "2020/10/22",
//         }
//         fields := map[string]interface{}{
//                 "description": "sample",
//                 "rewardpoint_date": "2020/10/22",
//                 "points": int64(50),
//                 "owner_id": int64(1),
//         }
//         m, err := metric.New("sample", tags, fields, now)
// 	assert.NoError(t, err)
// 
// 	s, _ := NewSerializer()
// 	var buf []byte
// 	buf, err = s.Serialize(m)
// 	assert.NoError(t, err)
// 
// 	assert.Equal(t, "null", string(buf))
// }
// 
// func TestSerializeBatch(t *testing.T) {
// 	m := MustMetric(
// 		metric.New(
// 			"sample",
// 			map[string]string{},
// 			map[string]interface{}{
//                             "description": "sample",
//                             "rewardpoint_date": "2020/10/22",
//                             "points": int64(50),
//                             "owner_id": int64(1),
// 			},
// 			time.Unix(0, 0),
// 		),
// 	)
// 
// 	metrics := []telegraf.Metric{m, m}
// 	s, _ := NewSerializer()
// 	buf, err := s.SerializeBatch(metrics)
// 	require.NoError(t, err)
// 	require.Equal(t, []byte(`[{"owner_id":1,"description":"sample","points":50,"date":"2020/10/22"}]`), buf)
// }
