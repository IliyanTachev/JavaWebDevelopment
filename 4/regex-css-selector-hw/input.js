regExps = {
"exercise_1": /[A-Z][a-z]+/,
"exercise_2": /(088)[^0]\d{6}/,
"exercise_3": /[^01]+/,
"exercise_4": /^[vi].*/,
"exercise_5": /(999|1[0-4][0-9][0-9]|1500)/,
"exercise_6": /\bclass=['|"].*['|"]/
};
cssSelectors = {
"exercise_1": "item > java",
"exercise_2": "#diffId java, #diffId2 java",
"exercise_3": "java .class1.class2",
"exercise_4": "item:nth-child(3):not(item item)",
"exercise_5": "tag java:nth-child(2)",
"exercise_6": "item[class=\"class2\"] > item, item[class=\"class1\"] > item",
"exercise_7": "#diffId2 java:nth-child(2)",
"exercise_8": "item:nth-child(2):not(item item)"
};
