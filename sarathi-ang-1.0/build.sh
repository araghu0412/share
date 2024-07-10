declare -r CURRENT_DIRECTORY=$PWD
declare -r CONFIG_DIRECTORY_LOCATION="F:/money-manager/Server/config/1.0/Angular"
declare -r SCSS_FILE_NAME="sarathi-ang-money-manager"

echo
echo "OM SHREE RAGHAVENDRAYA NAMAHA"
echo

echo "******* ******* ******* ******* ******* ******* *******"
echo
echo "Hello, Money Manager!!!"

# Getting the environment from the cmd line
echo "Getting the environment name to start the Sarathi app"
while [ $# -ge 1 ]; do
  case $1 in
    -env ) environment=$2; shift;;
    * )
  esac
  shift
done

if [ -z $environment ]
then
  environment="dev"
fi

echo "Environment is $environment"
echo

# Deleting environment file
cd $CURRENT_DIRECTORY
rm -rfv src/environments/environment.ts;
echo "Deleting environment file completed......."
echo

# Deleting scss file
cd $CURRENT_DIRECTORY
rm -rfv src/assets/styles/$SCSS_FILE_NAME.min.css;
echo "Deleting css file completed......."
echo

# Copying the environment file
echo "Coying the environment file to config.js"
if ! (`cp ${CONFIG_DIRECTORY_LOCATION}/$environment/environment.ts ${CURRENT_DIRECTORY}/src/environments/environment.ts`)
then
  echo "*********************"
  echo "******* ERROR *******"
  echo "*********************"
  echo
  echo "Error while copying config.js"
  echo "Please refer the above error for details"
  echo "Please press any key to continue"
  read user_input
  exit 1
fi
echo "Coying the environment file to config.js completed......."
echo

# Minify the css and copy it to the assest directory
echo "Minifying and copying the css"
if ! (`sass --no-source-map $CONFIG_DIRECTORY_LOCATION/$environment/$SCSS_FILE_NAME.scss $CURRENT_DIRECTORY/src/assets/styles/$SCSS_FILE_NAME.min.css --style=compressed`)
then
  echo "*********************"
  echo "******* ERROR *******"
  echo "*********************"
  echo
  echo "Error while minifying and copying the css"
  echo "Please refer the above error for details"
  echo "Please press any key to continue"
  read user_input
  exit 1
fi
echo "Minifying and copying the css completed......."
echo

cd $CURRENT_DIRECTORY

echo "******* ******* ******* ******* ******* ******* *******"
echo

# Starting Sarathi angular app
echo "Starting Sarathi angular app"
echo
ng serve
