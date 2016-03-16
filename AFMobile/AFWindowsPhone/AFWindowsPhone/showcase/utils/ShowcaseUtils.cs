using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;

namespace AFWindowsPhone.showcase
{
    class ShowcaseUtils
    {

        public static void setUserInPreferences(String username, String password)
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            localSettings.Values["username"] = username;
            localSettings.Values["password"] = password;
        }

        public static void clearUserInPreferences()
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            localSettings.Values.Remove("username");
            localSettings.Values.Remove("password");
        }

        public static Dictionary<String, String> getUserCredentials()
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            String username = (String) localSettings.Values["username"];
            String password = (String) localSettings.Values["password"];
            if (username != null && password != null)
            {
                Dictionary<String, String> result = new Dictionary<String, String>();
                result.Add("username", username);
                result.Add("password", password);
                return result;
            }
            return null;
        }

        public static String getUserLogin()
        {
            var localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
            String username = (String) localSettings.Values["username"];
            return username;
        }

    }
}
