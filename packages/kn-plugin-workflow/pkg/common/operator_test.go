/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package common

import (
	"testing"
)

var testCasesCheckOperatorRunning = []struct {
	name     string
	input    string
	expected bool
}{
	{
		name: "pod running",
		input: `NAME   READY   STATUS    RESTARTS   AGE
sonataflow-operator-controller-manager-78cb446b89-gj9jz   2/2     Running   0          95m`,
		expected: true,
	},
	{
		name:     "no pods",
		input:    "No resources found in sonataflow-operator-system namespace.",
		expected: false,
	},
	{
		name:     "no pods - empty string",
		input:    "",
		expected: false,
	},
	{
		name:     "no pods - - empty string 2",
		input:    " ",
		expected: false,
	},
	{
		name:     "no pods - some return",
		input:    " some weird return ",
		expected: false,
	},
}

func TestCheckOperatorRunning(t *testing.T) {
	for _, tc := range testCasesCheckOperatorRunning {
		t.Run(tc.name, func(t *testing.T) {
			result := checkOperatorRunning(tc.input)
			if result != tc.expected {
				t.Errorf("Expected %v, but got %v", tc.expected, result)
			}
		})
	}
}
