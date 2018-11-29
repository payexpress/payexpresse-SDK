


/**
 * Created by PhpStorm.
 * User: macbookpro
 * Date: 30/09/2017
 * Time: 14:35
 */
class PayExpresse(object):
    /**
     * @var string
     */
    const URL = "https://payexpresse.com"
    const PAYMENT_REQUEST_PATH = '/api/payment/request-payment'
    const PAYMENT_REDIRECT_PATH = '/payment/checkout/'//todo
    //const URL = "http://localhost:5008"//todo
    const MOBILE_CANCEL_URL = "https://payexpresse.com/mobile/cancel"
    const MOBILE_SUCCESS_URL = "https://payexpresse.com/mobile/success"
    const THEME_OPTION_KEY = '__themeOption__'
    /**
     * @var string
     */
    private apiKey
    /**
     * @var string
     */
    private apiSecret
    /**
     * @var array
     */
    private query = []

    private params = []

    /**
     * @var array
     */
    private customeField = []

    private liveMode = true

    private testMode = false

    private isMobile = false

    private currency = 'XOF'

    private refCommand = ''

    private notificationUrl = []


    def __init__(self,apiKey, apiSecret):

        self.setApiKey(apiKey)
        self.setApiSecret(apiSecret)

        if _POST['is_mobile']) && _POST['is_mobile'] === 'yes':
            self.isMobile = true

    /**
     * @param string apiKey
     */
    def setApiKey(self,apiKey):
        self.apiKey = apiKey

    /**
     * @param string apiSecret
     */
    def setApiSecret(self,apiSecret):
        self.apiSecret = apiSecret

    /**
     * @return array
     */
    def send(self):

        params = [
            'item_name' => PayExpresse.arrayGet(self.query, 'item_name'),
            'item_price' => PayExpresse.arrayGet(self.query, 'item_price'),
            'command_name' => PayExpresse.arrayGet(self.query, 'command_name'),
            'ref_command' => self.refCommand,
            'env' => (self.testMode) ? 'test' : 'prod',
            'currency' => self.currency,
            'ipn_url' => PayExpresse.arrayGet(self.notificationUrl, 'ipn_url'),
            'success_url' => self.isMobile ? this.MOBILE_SUCCESS_URL : PayExpresse.arrayGet(self.notificationUrl, 'success_url'),
            'cancel_url' => self.isMobile ? this.MOBILE_CANCEL_URL : PayExpresse.arrayGet(self.notificationUrl, 'cancel_url'),
            'custom_field' => json_encode(self.customeField)
        ]

        rawResponse = PayExpresse.post(this.URL . this.PAYMENT_REQUEST_PATH, params, [
            "API_KEY: {self.apiKey}",
            "API_SECRET: {self.apiSecret}"
        ])

        /**
         * @var array
         */
        jsonResponse = json_decode(rawResponse, true)

        if 'token', jsonResponse):
            query = ''
            if _POST[this.THEME_OPTION_KEY]):
                query = '?t=' . _POST[this.THEME_OPTION_KEY]

            return [
                'success' => 1,
                'token' => jsonResponse['token'],
                'redirect_url' => this.URL . this.PAYMENT_REDIRECT_PATH . jsonResponse['token'] . query
            ]
            return [
                'success' => -1,
                'errors' => jsonResponse['error']
            ]
        else :
            return [
                'success' => -1,
                'errors' => [
                    'Internal Error'
                ]
            ]


    private static function arrayGet(array, name, default = '')
        return empty(array[name]) ? default : array[name]

    private static function post(url, data = [], header = [])
        strPostField = http_build_query(data)

        ch = curl_init(url)
        curl_setopt(ch, CURLOPT_CUSTOMREQUEST, "POST")
        curl_setopt(ch, CURLOPT_POSTFIELDS, strPostField)
        curl_setopt(ch, CURLOPT_RETURNTRANSFER, true)
        curl_setopt(ch, CURLOPT_SSL_VERIFYPEER, 0)
        curl_setopt(ch, CURLOPT_SSL_VERIFYHOST, 0)

        curl_setopt(ch, CURLOPT_HTTPHEADER, array_merge(header, [
            'Content-Type: application/x-www-form-urlencodedcharset=utf-8',
            'Content-Length: ' . mb_strlen(strPostField)
        ]))

        return curl_exec(ch)

    /**
     * @param array query
     * @return this
     */
    def setQuery(self,query):
        self.query = query
        return this


    /**
     * @param array customeField
     * @return this
     */
    def setCustomeField(self,customeField):
        if customeField):
            self.customeField = customeField

        return this


    /**
     * @param bool liveMode
     * @return this
     */
    def setLiveMode(self,liveMode):
        self.liveMode = liveMode
        self.testMode = !liveMode

        return this


    /**
     * @param bool testMode
     * @return this
     */
    def setTestMode(self,testMode):
        self.testMode = testMode
        self.liveMode = !testMode

        return this


    /**
     * @param string currency
     * @return this
     */
    def setCurrency(self,currency):
        self.currency = strtolower(currency)
        return this


    /**
     * @param string refCommand
     * @return this
     */
    def setRefCommand(self,refCommand):
        self.refCommand = refCommand

        return this


    /**
     * @param array notificationUrl
     * @return this
     */
    def setNotificationUrl(self,notificationUrl):
        self.notificationUrl = notificationUrl
        return this


    /**
     * @param bool isMobile
     * @return this
     */
    def setMobile(self,isMobile):
        self.isMobile = isMobile

        return this


