class LoadData {

        void run() {
            personList = new ArrayList<>();
            getData();
        }

        protected void showList() {
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String id = c.getString(TAG_ID);
                    String name = c.getString(TAG_NAME);

                    System.out.println(id + " " + name);

                    HashMap<String, String> persons = new HashMap<>();

                    persons.put(TAG_ID, id);
                    persons.put(TAG_NAME, name);

                    personList.add(persons);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        public void getData() {
            class GetDataJSON extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {
                    DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                    HttpPost httppost = new HttpPost("http://sdgp.uk.ht/getData.php");

                    // Depends on your web service
                    httppost.setHeader("Content-type", "application/json");

                    InputStream inputStream = null;
                    String result = null;
                    try {
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();

                        inputStream = entity.getContent();
                        // json is UTF-8 by default
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        result = sb.toString();
                    } catch (Exception e) {
                        // Oops
                    } finally {
                        try {
                            if (inputStream != null) inputStream.close();
                        } catch (Exception squish) {
                        }
                    }

                    System.out.println(result);
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    myJSON = result;
                    showList();
                }
            }
            GetDataJSON g = new GetDataJSON();
            g.execute();
        }
    }